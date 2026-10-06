$proyecto = "PSP-Practicas"
ssh pspvm "mkdir -p ~/$proyecto"
scp -r bin/* pspvm:~/$proyecto/
Write-Host "Desplegado en ~/$proyecto" -ForegroundColor Green
