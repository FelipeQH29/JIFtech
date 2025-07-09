-- ================================
-- CREACIÓN DE BASE Y USUARIO
-- ================================
DROP SCHEMA IF EXISTS JIF;
DROP USER IF EXISTS 'usuario_prueba'@'%';

CREATE SCHEMA JIF;
CREATE USER 'usuario_prueba'@'%' IDENTIFIED BY 'Usuario_Clave.';
GRANT ALL PRIVILEGES ON JIF.* TO 'usuario_prueba'@'%';
FLUSH PRIVILEGES;
USE JIF;

-- ================================
-- TABLA: CATEGORÍA (SIMPLIFICADA)
-- ================================
CREATE TABLE categoria (
  id_categoria INT AUTO_INCREMENT PRIMARY KEY,
  nombre       VARCHAR(50) NOT NULL,
  descripcion  TEXT,
  icono_url    VARCHAR(3000),
  activo       BOOLEAN DEFAULT TRUE
);

-- Insertar 3 categorías base
INSERT INTO categoria (nombre, descripcion) VALUES
  ('Consolas',    'Consolas clásicas y retro'),
  ('Accesorios',  'Controles, cables y periféricos'),
  ('Juegos',      'Videojuegos físicos o digitales');

-- ================================
-- TABLA: PRODUCTO
-- ================================
CREATE TABLE producto (
  id_producto   INT AUTO_INCREMENT PRIMARY KEY,
  nombre        VARCHAR(100) NOT NULL,
  descripcion   TEXT,
  precio        DECIMAL(10,2),
  id_categoria  INT NOT NULL,
  ruta_imagen   VARCHAR(3000),
  stock         INT DEFAULT 0,
  estado        VARCHAR(20) DEFAULT 'Usado',
  activo        BOOLEAN DEFAULT TRUE,
  FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);

-- Productos de ejemplo
INSERT INTO producto (nombre, descripcion, precio, id_categoria, ruta_imagen, stock, estado)
VALUES
  -- Consolas
  ('PlayStation 1',    'Consola retro de Sony',             80.00, 1, 'https://cdn.britannica.com/37/141537-050-AA59DCA6/console-PlayStation.jpg',                5, 'Usado'),
  ('Nintendo 64',      'Icónica consola de 64 bits',       100.00,1, 'https://static.gamesmen.com.au/media/catalog/product/cache/43c1b9e48526c06c9c8010675100b71d/n/i/nintendo_64_charcoal_black_console_pre-owned_5__1.jpg',               3, 'Usado'),
  ('Super Nintendo',   'Clásica consola de 16 bits',       90.00, 1, 'https://cdn.britannica.com/65/253765-004-222811C5/Original-Nintendo-video-game-system-with-cartridges.jpg',              4, 'Usado'),
  ('SEGA Genesis',     'Consola icónica de SEGA',          75.00, 1, 'https://i.guim.co.uk/img/media/2cb52148014bf8314dda70978c6e8d87f2aca89a/784_1290_2411_1446/master/2411.jpg?width=1900&dpr=1&s=none&crop=none',            2, 'Usado'),
  ('GameCube',         'Consola compacta de Nintendo',     85.00, 1, 'https://cdn11.bigcommerce.com/s-ez2sytmqce/images/stencil/1280x1280/products/35775/43222/20241219_162602__36538.1736389602.jpg?c=2',           3, 'Usado'),
  ('Dreamcast',        'Última consola de SEGA',           95.00, 1, 'https://images-cdn.ubuy.co.in/635170351e58900fb07fdff2-sega-dreamcast-console-renewed.jpg',          2, 'Usado'),
  -- Accesorios
  ('DualShock PS1',    'Control original para PS1',        20.00, 2, 'https://i.blogs.es/05502a/sony-control-pad/1366_2000.jpg',       10,'Usado'),
  ('N64 Joystick',     'Controlador original N64',         30.00, 2, 'https://substackcdn.com/image/fetch/$s_!4NZn!,w_1456,c_limit,f_webp,q_auto:good,fl_progressive:steep/https%3A%2F%2Fbucketeer-e05bbc84-baa3-437e-9518-adb32be77984.s3.amazonaws.com%2Fpublic%2Fimages%2F7924a22f-f6ef-4657-b086-a388be29b208_900x506.jpeg',      8, 'Usado'),
  ('SNES Gamepad',     'Control clásico Super Nintendo',   25.00, 2, 'https://www.the-liberator.net/site-files/retro-games/hardware/Nintendo-Super-NES-Controller/thumbs/Super-Nintendo-SNES-Controller-001_small.jpg',      6, 'Usado'),
  ('SEGA Genesis Ctrl','Control original SEGA Genesis',    22.00, 2, 'https://segaretro.org/images/thumb/3/30/Pad_MD_Gen3.jpg/320px-Pad_MD_Gen3.jpg',   5, 'Usado'),
  ('GameCube Ctrl',    'Control oficial GameCube',         28.00, 2, 'https://gagadget.es/media/cache/9f/b2/9fb2584531c3939e8853238e41fb7259.jpg',       7, 'Usado'),
  ('Dreamcast VMU',    'Unidad de memoria Dreamcast',      15.00, 2, 'https://platform.theverge.com/wp-content/uploads/sites/2/2025/05/8bitmods_vmupro1.jpg?quality=90&strip=all&crop=0%2C0%2C100%2C100&w=750',                4, 'Usado'),
  -- Juegos
  ('Super Mario 64',        'Juego clásico para N64',         45.00, 3, 'https://www.guiasnintendo.com/4_Nintendo_64/mario64/mario64_sp/img/inicio.jpg',   4, 'Usado'),
  ('Crash Bandicoot',       'Juego para PS1',                 35.00, 3, 'https://i.3djuegos.com/juegos/10692/crash_bandicoot/fotos/ficha/crash_bandicoot-2464078.jpg',             6, 'Usado'),
  ('Zelda Ocarina of Time', 'Aventura épica para N64',        50.00, 3, 'https://i.3djuegos.com/juegos/3866/the_legend_of_zelda_ocarina_of_time/fotos/ficha/the_legend_of_zelda_ocarina_of_time-1692465.jpg',    3, 'Usado'),
  ('Donkey Kong Country',   'Juego clásico de SNES',          40.00, 3, 'https://i.3djuegos.com/juegos/9285/donkey_kong_country/fotos/ficha/donkey_kong_country-2108022.jpg',      5, 'Usado'),
  ('Silent Hill 2',  'Clasico de terror de ps2',       12.000, 3, 'https://i.3djuegos.com/juegos/5182/silent_hill_2/fotos/ficha/silent_hill_2-1697791.jpg',            4, 'Usado'),
  ('Resident Evil 2',       'Survival horror para PS1',       38.00, 3, 'https://i.3djuegos.com/juegos/6037/resident_evil_2/fotos/ficha/resident_evil_2-1720208.jpg',              3, 'Usado');

-- ================================
-- TABLA: USUARIO
-- ================================
CREATE TABLE usuario (
  id_usuario  INT AUTO_INCREMENT PRIMARY KEY,
  username    VARCHAR(20) NOT NULL,
  password    VARCHAR(512) NOT NULL,
  nombre      VARCHAR(20) NOT NULL,
  apellidos   VARCHAR(30) NOT NULL,
  correo      VARCHAR(75),
  telefono    VARCHAR(15),
  ruta_imagen VARCHAR(1024),
  activo      BOOLEAN
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ================================
-- TABLA: ROL
-- ================================
CREATE TABLE rol (
  id_rol     INT AUTO_INCREMENT PRIMARY KEY,
  nombre     VARCHAR(20),
  id_usuario INT,
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ================================
-- TABLA: CONTACTO
-- ================================
CREATE TABLE contacto (
  id_contacto INT AUTO_INCREMENT PRIMARY KEY,
  name        VARCHAR(100) NOT NULL,
  email       VARCHAR(100) NOT NULL,
  message     TEXT         NOT NULL,
  fecha_envio DATETIME     DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ================================
-- TABLAS: FACTURA Y VENTA
-- ================================
CREATE TABLE factura (
  id_factura INT AUTO_INCREMENT PRIMARY KEY,
  id_usuario INT NOT NULL,
  fecha      DATE,
  total      DECIMAL(10,2),
  estado     INT,
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE venta (
  id_venta    INT AUTO_INCREMENT PRIMARY KEY,
  id_factura  INT NOT NULL,
  id_producto INT NOT NULL,
  precio      DECIMAL(10,2),
  cantidad    INT,
  FOREIGN KEY (id_factura) REFERENCES factura(id_factura),
  FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ================================
-- TABLAS: REQUEST MATCHERS (seguridad)
-- ================================
CREATE TABLE request_matcher (
  id_request_matcher INT AUTO_INCREMENT PRIMARY KEY,
  pattern            VARCHAR(255) NOT NULL,
  role_name          VARCHAR(50)  NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO request_matcher (pattern, role_name) VALUES
  ('/producto/nuevo',      'ADMIN'),
  ('/producto/guardar',    'ADMIN'),
  ('/producto/modificar/**','ADMIN'),
  ('/producto/eliminar/**','ADMIN'),
  ('/categoria/nuevo',     'ADMIN'),
  ('/categoria/guardar',   'ADMIN'),
  ('/categoria/modificar/**','ADMIN'),
  ('/categoria/eliminar/**','ADMIN'),
  ('/usuario/nuevo',       'ADMIN'),
  ('/usuario/guardar',     'ADMIN'),
  ('/usuario/modificar/**','ADMIN'),
  ('/usuario/eliminar/**','ADMIN'),
  ('/reportes/**',         'ADMIN'),
  ('/producto/listado',    'VENDEDOR'),
  ('/categoria/listado',   'VENDEDOR'),
  ('/usuario/listado',     'VENDEDOR'),
  ('/facturar/carrito',    'USER');

CREATE TABLE request_matcher_all (
  id_request_matcher INT AUTO_INCREMENT PRIMARY KEY,
  pattern            VARCHAR(255)  NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO request_matcher_all (pattern) VALUES
  ('/'),
  ('/index'),
  ('/errores/**'),
  ('/carrito/**'),
  ('/pruebas/**'),
  ('/reportes/**'),
  ('/registro/**'),
  ('/js/**'),
  ('/webjars/**');
