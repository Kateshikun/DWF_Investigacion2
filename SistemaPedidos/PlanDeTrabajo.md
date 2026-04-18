

# Distribución del Equipo

## 👤 Rudy
## 🧩 Pasos a seguir

### 1. Crear repositorio

* Extender `JpaRepository`

### 2. Crear servicio

* Métodos CRUD 
* Guardar en base de datos
* ⚠️ IMPORTANTE: Tenes que lanzar un evento en el metodo de crear pedido para que los listeners en la carpeta Events lo agarren (eso lo hara Colocho 2 JAJAJAJ)

---

### 3. Crear controller

Endpoints recomendables:

* POST `/orders`
* GET `/orders`
* GET `/orders/{id}`
* PUT `/orders/{id}`
* DELETE `/orders/{id}`

---

### 4. Integración con eventos

* Inyectar `ApplicationEventPublisher`
* Disparar evento al crear pedido: Osea, solo vas a disparar un evento en el metodo de crearPedido() nada mas.

---

### 🚨 Reglas

* NO meter lógica de negocio en el controller
* NO llamar RabbitMQ directamente
* SOLO CRUD y guardar y disparar evento

---

---

# 👤 Colocho 2 – Event-Driven + Mensajería

## 🧩 Pasos a seguir

### 1. Crear evento

* `OrderCreatedEvent` <-- Clase principal para escuchar eventos que el service te pasara

---

### 2. Crear listeners internos

Ejemplos:

* actualizar estado
* logging
* Podes agregar mas
---

### 3. Crear configuración de RabbitMQ

* Cola (`Queue`)
* Exchange (preferiblemente tipo fanout)
* Binding

---

### 4. Crear producer (envío de mensajes): El que envia el mensaje al usuario

* Clase que use `RabbitTemplate`

---

### 5. Crear bridge (evento → mensajería)

* Listener que reciba evento y mande a RabbitMQ

---

### 6. Crear consumers: El que recibe el mensaje que producer envio

Separar responsabilidades:

* PaymentConsumer
* NotificationConsumer
* LogConsumer

---

### 7. Implementar asincronía

* Usar `@Async`
* Configurar `@EnableAsync`

---

### Reglas

* NO duplicar lógica entre listeners y consumers
* OJO: Cada consumer debe hacer UNA tarea
* Mantener desacoplamiento

---

---

# Rodrigo – Docker + Kubernetes (DevOps)

## 🧩 Pasos a seguir

### 1. Generar `.jar`

```bash
mvn clean package
```

---

### 2. Crear `Dockerfile`

* Base: OpenJDK
* Copiar `.jar`
* Ejecutar app

---

### 3. Crear `docker-compose.yml`

Servicios:

* API
* RabbitMQ

---

### 4. Configurar conexión

⚠️ IMPORTANTE:

* Usar `rabbitmq` como hostname (NO localhost)

---

### 5. Probar ejecución

```bash
docker-compose up --build
```

---

### 6. Crear configuración de Kubernetes

#### Deployment API

* 2 réplicas mínimo

#### Service API

* Tipo NodePort

---

### 7. (Opcional pero recomendado)

Deployment + Service para RabbitMQ

---

### 8. Ejecutar en Kubernetes

```bash
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
```

---

### 🚨 Reglas

* Verificar que la imagen esté disponible
* No usar localhost en configs
* Asegurar que todo levante correctamente

---

---

## Flujo final del sistema

1. Controller recibe petición
2. Service guarda pedido
3. Service dispara evento
4. Listener interno procesa lógica
5. Listener envía mensaje a RabbitMQ
6. RabbitMQ distribuye
7. Consumers ejecutan tareas

---

