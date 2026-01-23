#!/bin/bash

# Verificamos si nos has dado la URL del repo
if [ -z "$1" ]; then
    echo "❌ Error: Te falta poner la URL del repositorio."
    echo "Uso correcto: ./subir.sh <URL_DE_GITHUB>"
    exit 1
fi

URL_REPO=$1
MY_EMAIL="131453300+aparreno14@users.noreply.github.com"

echo "🚀 Iniciando protocolo de subida..."

# 1. Iniciar o reinicializar git
git init

# 2. Configurar tu email privado (CRÍTICO para evitar errores)
git config user.email "$MY_EMAIL"
echo "✅ Email de privacidad configurado."

# 3. Preparar archivos
git add .

# 4. Crear el commit
git commit -m "Automatic upload via script"

# 5. Configurar rama y remoto
git branch -M main

# (Borramos origin por si acaso existía uno viejo roto)
git remote remove origin 2> /dev/null 
git remote add origin "$URL_REPO"


# 6. Subir
echo "☁️ Subiendo a GitHub..."
git push -u origin main

echo "🎉 ¡Éxito! Tu proyecto ya está en la nube."