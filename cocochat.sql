-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Oct 25, 2023 at 05:11 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cocochat`
--

-- --------------------------------------------------------

--
-- Table structure for table `Amistad`
--

CREATE TABLE `Amistad` (
  `id_amistad` int(11) NOT NULL,
  `usuario_origen` int(11) NOT NULL,
  `usuario_destino` int(11) NOT NULL,
  `id_chat` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Chats`
--

CREATE TABLE `Chats` (
  `id_chat` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Grupo`
--

CREATE TABLE `Grupo` (
  `id_grupo` int(11) NOT NULL,
  `id_chat` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Grupo_usuarios`
--

CREATE TABLE `Grupo_usuarios` (
  `id_grupo_usuarios` int(11) NOT NULL,
  `id_grupo` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Mensaje`
--

CREATE TABLE `Mensaje` (
  `id_mensaje` int(11) NOT NULL,
  `id_chat` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `hora_mensaje` datetime NOT NULL,
  `mensaje` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Usuarios`
--

CREATE TABLE `Usuarios` (
  `id_usuario` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `contra` varchar(50) NOT NULL,
  `conectado` tinyint(1) NOT NULL,
  `fecha_registro_fallido` datetime NOT NULL,
  `registros_fallidos` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Amistad`
--
ALTER TABLE `Amistad`
  ADD PRIMARY KEY (`id_amistad`),
  ADD KEY `Relacion U_Destino` (`usuario_destino`),
  ADD KEY `Relacion U_Origen` (`usuario_origen`),
  ADD KEY `Relacion Id_Chat` (`id_chat`);

--
-- Indexes for table `Chats`
--
ALTER TABLE `Chats`
  ADD PRIMARY KEY (`id_chat`);

--
-- Indexes for table `Grupo`
--
ALTER TABLE `Grupo`
  ADD PRIMARY KEY (`id_grupo`),
  ADD KEY `id_chat` (`id_chat`);

--
-- Indexes for table `Grupo_usuarios`
--
ALTER TABLE `Grupo_usuarios`
  ADD PRIMARY KEY (`id_grupo_usuarios`),
  ADD KEY `grupo_usuarios_ibfk_1` (`id_grupo`),
  ADD KEY `grupo_usuarios_ibfk_2` (`id_usuario`);

--
-- Indexes for table `Mensaje`
--
ALTER TABLE `Mensaje`
  ADD PRIMARY KEY (`id_mensaje`),
  ADD KEY `id_usuario` (`id_usuario`),
  ADD KEY `id_chat` (`id_chat`);

--
-- Indexes for table `Usuarios`
--
ALTER TABLE `Usuarios`
  ADD PRIMARY KEY (`id_usuario`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Amistad`
--
ALTER TABLE `Amistad`
  MODIFY `id_amistad` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Chats`
--
ALTER TABLE `Chats`
  MODIFY `id_chat` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Grupo`
--
ALTER TABLE `Grupo`
  MODIFY `id_grupo` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Grupo_usuarios`
--
ALTER TABLE `Grupo_usuarios`
  MODIFY `id_grupo_usuarios` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Mensaje`
--
ALTER TABLE `Mensaje`
  MODIFY `id_mensaje` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Usuarios`
--
ALTER TABLE `Usuarios`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Amistad`
--
ALTER TABLE `Amistad`
  ADD CONSTRAINT `Relacion Id_Chat` FOREIGN KEY (`id_chat`) REFERENCES `Chats` (`id_chat`),
  ADD CONSTRAINT `Relacion U_Destino` FOREIGN KEY (`usuario_destino`) REFERENCES `Usuarios` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Relacion U_Origen` FOREIGN KEY (`usuario_origen`) REFERENCES `Usuarios` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Grupo`
--
ALTER TABLE `Grupo`
  ADD CONSTRAINT `grupo_ibfk_1` FOREIGN KEY (`id_chat`) REFERENCES `Chats` (`id_chat`);

--
-- Constraints for table `Grupo_usuarios`
--
ALTER TABLE `Grupo_usuarios`
  ADD CONSTRAINT `grupo_usuarios_ibfk_1` FOREIGN KEY (`id_grupo`) REFERENCES `Grupo` (`id_grupo`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `grupo_usuarios_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `Usuarios` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Mensaje`
--
ALTER TABLE `Mensaje`
  ADD CONSTRAINT `mensaje_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `Usuarios` (`id_usuario`),
  ADD CONSTRAINT `mensaje_ibfk_2` FOREIGN KEY (`id_chat`) REFERENCES `Chats` (`id_chat`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
