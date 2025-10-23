[CmdletBinding()]
param(
  [switch]$Visible,
  [string]$Class,
  [string]$Method,
  [switch]$SkipInstall,
  [string]$MavenPath
)

# Navigate to the repo root (where this script lives)
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $scriptDir

# Resolve Maven executable
if (-not $MavenPath) {
  $defaultMaven = Join-Path $env:USERPROFILE 'apache-maven-3.9.11\bin\mvn.cmd'
  if (Test-Path $defaultMaven) {
    $MavenPath = $defaultMaven
  } else {
    $mvnCmd = Get-Command mvn -ErrorAction SilentlyContinue
    if ($mvnCmd) { $MavenPath = $mvnCmd.Source } else { throw 'Maven no encontrado. Instálalo o especifica -MavenPath.' }
  }
}

# Build properties
$props = @()
if (-not $SkipInstall) { $props += '-Dplaywright.install=true' }
if ($Visible) { $props += '-DPLAYWRIGHT_HEADLESS=false' }

# Test selector
$testSelector = $null
if ($Class) {
  if ($Method) {
    $testSelector = "-Dtest=$Class#$Method"
  } else {
    $testSelector = "-Dtest=$Class"
  }
}

# Compose args (quote -D props to avoid PowerShell splitting)
$cmdArgs = @()
foreach ($p in $props) { $cmdArgs += "`"$p`"" }
if ($testSelector) { $cmdArgs += "`"$testSelector`"" }
$cmdArgs += 'test'

Write-Host "Ejecutando:" $MavenPath ($cmdArgs -join ' ') -ForegroundColor Cyan

& $MavenPath @cmdArgs
$exitCode = $LASTEXITCODE

if ($exitCode -ne 0) {
  Write-Error "Ejecución fallida con código $exitCode. Revisa target/surefire-reports."
  exit $exitCode
}

Write-Host "Listo. Reportes en target/surefire-reports" -ForegroundColor Green
