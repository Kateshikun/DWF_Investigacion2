Cómo ejecutar el proyecto (Docker Kubernetes Minikube)
1. Requisitos

Instalar:
Docker
Minikube
kubectl

2. Clonar el repositorio

3. Iniciar Kubernetes local
minikube start

Construir construir una imagen del docker del proyecto

kubectl apply -f k8s/ para los archivos .yaml

Verificar que todo esté corriendo
kubectl get pods

Espera a que diga Running

minikube service sistemapedidos-service --url
esto para que de la url y hacer las peticiones

Comprobaciones
<img width="1600" height="1004" alt="image" src="https://github.com/user-attachments/assets/58fa1edd-3d9b-483a-9239-231b9efeab73" />
<img width="1600" height="839" alt="image" src="https://github.com/user-attachments/assets/f0f30f42-799d-4c47-848b-7ae8bae489ff" />
