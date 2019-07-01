-- TABLA SPRINTS
CREATE TABLE sprints (
                id_sprint NUMERIC NOT NULL,
                id_proyecto VARCHAR NOT NULL,
                fecha_inicio DATE NOT NULL,
                fecha_fin_estimado DATE,
                fecha_fin_real DATE,
                duracion_estimada NUMERIC NOT NULL,
                duracion_real NUMERIC,
                story_points_estimados NUMERIC NOT NULL,
                story_points_realizados NUMERIC,
                estado_sprint INTEGER NOT NULL,
                CONSTRAINT pk_sprints PRIMARY KEY (id_sprint)
);


CREATE SEQUENCE usuarios_id_usuario_seq;

-- TABLA USUARIOS
CREATE TABLE usuarios (
                id_usuario INTEGER NOT NULL DEFAULT nextval('usuarios_id_usuario_seq'),
                nombre_apellido VARCHAR NOT NULL,
                cedula INTEGER NOT NULL,
                correo_electronico VARCHAR NOT NULL,
                nick_usuario VARCHAR NOT NULL,
                password VARCHAR NOT NULL,
                activo BOOLEAN DEFAULT true NOT NULL,
                id_rol_sistema INTEGER NOT NULL,
                CONSTRAINT usuarios_pk PRIMARY KEY (id_usuario)
);

ALTER SEQUENCE usuarios_id_usuario_seq OWNED BY usuarios.id_usuario;

ALTER TABLE usuarios 
ADD CONSTRAINT unique_nick UNIQUE (nick_usuario);

ALTER TABLE usuarios 
ADD CONSTRAINT unique_cedula UNIQUE (cedula);

CREATE SEQUENCE roles_id_rol_seq;

-- TABLA ROLES
CREATE TABLE roles (
                id_rol INTEGER NOT NULL DEFAULT nextval('roles_id_rol_seq'),
                nombre VARCHAR NOT NULL,
                descripcion VARCHAR NOT NULL,
                CONSTRAINT roles_pk PRIMARY KEY (id_rol)
);


ALTER SEQUENCE roles_id_rol_seq OWNED BY roles.id_rol;

-- TABLA PROYECTOS
CREATE TABLE proyectos (
                id_proyecto VARCHAR NOT NULL,
                nombre VARCHAR NOT NULL,
                duedate DATE NOT NULL,
                descripcion VARCHAR,
                activo BOOLEAN DEFAULT true NOT NULL,
                CONSTRAINT proyectos_pk PRIMARY KEY (id_proyecto)
);

-- TABLA ROLES_USUARIOS
CREATE TABLE roles_usuarios (
                id_rol INTEGER NOT NULL,
                id_usuario INTEGER NOT NULL,
                id_proyecto VARCHAR NOT NULL,
                CONSTRAINT roles_usuarios_pk PRIMARY KEY (id_rol, id_usuario, id_proyecto)
);

CREATE SEQUENCE prioridades_id_prio_seq;

-- TABLA PRIORIDADES
CREATE TABLE prioridades (
                id_prio INTEGER NOT NULL DEFAULT nextval('prioridades_id_prio_seq'),
                descripcion VARCHAR NOT NULL,
                nivel VARCHAR NOT NULL,
                CONSTRAINT prioridades_pk PRIMARY KEY (id_prio)
);


ALTER SEQUENCE prioridades_id_prio_seq OWNED BY prioridades.id_prio;

CREATE SEQUENCE estados_id_estados_seq;

-- TABLA ESTADOS
CREATE TABLE estados (
                id_estados INTEGER NOT NULL DEFAULT nextval('estados_id_estados_seq'),
                descripcion VARCHAR NOT NULL,
                CONSTRAINT estados_pk PRIMARY KEY (id_estados)
);


ALTER SEQUENCE estados_id_estados_seq OWNED BY estados.id_estados;

-- TABLA USER STORIES
CREATE TABLE userstories (
                id_proyecto VARCHAR NOT NULL,
                id_us VARCHAR NOT NULL,
                nombre VARCHAR NOT NULL,
                descripcion VARCHAR NOT NULL,
                tiempo_estimado INTEGER NOT NULL,
                tiempo_trabajado INTEGER NOT NULL,
                id_estados INTEGER NOT NULL,
                id_estados_backlog INTEGER NOT NULL,
                id_prio INTEGER NOT NULL,
                id_responsable INTEGER NOT NULL,
                id_sprint NUMERIC,
                story_points INTEGER,
                fecha_modificacion DATE,
                CONSTRAINT userstories_pk PRIMARY KEY (id_proyecto, id_us)
);

--/**********************CONSTRAINTS**************************/
ALTER TABLE roles_usuarios ADD CONSTRAINT usuario_rol_usuario_fk
FOREIGN KEY (id_usuario)
REFERENCES usuarios (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE roles_usuarios ADD CONSTRAINT rol_rol_usuario_fk
FOREIGN KEY (id_rol)
REFERENCES roles (id_rol)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE roles_usuarios ADD CONSTRAINT proyectos_roles_usuarios_fk
FOREIGN KEY (id_proyecto)
REFERENCES proyectos (id_proyecto)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE userstories ADD CONSTRAINT sprint_userstory_fk
FOREIGN KEY (id_sprint)
REFERENCES sprints (id_sprint)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE userstories ADD CONSTRAINT proyecto_userstory_fk
FOREIGN KEY (id_proyecto)
REFERENCES proyectos (id_proyecto)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE userstories ADD CONSTRAINT prioridades_userstories_fk
FOREIGN KEY (id_prio)
REFERENCES prioridades (id_prio)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE userstories ADD CONSTRAINT estados_userstory_fk
FOREIGN KEY (id_estados)
REFERENCES estados (id_estados)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE userstories ADD CONSTRAINT estados_userstory_fk1
FOREIGN KEY (id_estados_backlog)
REFERENCES estados (id_estados)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE userstories ADD CONSTRAINT fd_propiedades_usuario_const
FOREIGN KEY (id_responsable)
REFERENCES usuarios (id_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE sprints ADD CONSTRAINT proyectos_sprints_fk
FOREIGN KEY (id_proyecto)
REFERENCES proyectos(id_proyecto)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE sprints ADD CONSTRAINT estado_sprint_fk
FOREIGN KEY (estado_sprint)
REFERENCES estados (id_estados)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE usuarios ADD CONSTRAINT id_rol_sistema_fk
FOREIGN KEY (id_rol_sistema)
REFERENCES roles (id_rol)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;