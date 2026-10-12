param([string]$clase)
& "$PSScriptRoot\deploy.ps1"
ssh -tt pspvm "cd ~/PSP-Practicas && java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005 $clase"
