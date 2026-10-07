$proyecto = "PSP-Practicas"
ssh pspvm "rm -rf ~/$proyecto && mkdir -p ~/$proyecto"
scp -r bin/* pspvm:~/$proyecto/
Write-Host "Desplegado en ~/$proyecto" -ForegroundColor Green
