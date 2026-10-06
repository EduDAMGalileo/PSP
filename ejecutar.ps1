# Windows · ejecutar.ps1
param([string]$clase)
& "$PSScriptRoot\deploy.ps1"
ssh -tt pspvm "cd ~/PSP-Practicas && java $clase"
