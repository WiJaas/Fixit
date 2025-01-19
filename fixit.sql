-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : dim. 19 jan. 2025 à 16:34
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `fixit`
--

-- --------------------------------------------------------

--
-- Structure de la table `incident`
--

CREATE TABLE `incident` (
  `incident_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `status` enum('Open','In Progress','Resolved') DEFAULT 'Open',
  `priority` enum('High','Medium','Low') DEFAULT 'Low',
  `type` enum('Problèmes Techniques','Problèmes Employés','Incidents de Sécurité','Problèmes de Performance') NOT NULL,
  `created_by` int(11) NOT NULL,
  `assigned_to` int(11) DEFAULT NULL,
  `creation_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `resolution_date` timestamp NULL DEFAULT NULL,
  `feedback` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `incident`
--

INSERT INTO `incident` (`incident_id`, `title`, `description`, `status`, `priority`, `type`, `created_by`, `assigned_to`, `creation_date`, `resolution_date`, `feedback`) VALUES
(17, 'Password Reset', 'I can\'t acess my Teams account because I forgot my password', 'Resolved', 'High', 'Problèmes Employés', 27, 34, '2025-01-12 16:31:22', '2025-01-12 16:31:22', ''),
(18, 'PC Charger', 'I need a new PC charger', 'Resolved', 'Medium', 'Problèmes Techniques', 26, 34, '2025-01-12 21:46:14', '2025-01-12 21:46:14', ''),
(26, 'HardDesk problem', 'details	', 'Resolved', 'Medium', 'Problèmes Techniques', 26, 34, '2025-01-15 12:15:32', NULL, 'Great Work!'),
(27, 'Network Problem', 'Details	', 'Resolved', 'High', 'Problèmes Techniques', 26, 34, '2025-01-15 14:26:46', '2025-01-15 14:32:21', NULL),
(28, 'System Crash', 'The system crashed during a critical operation', 'Open', 'High', 'Problèmes Techniques', 26, NULL, '2025-01-17 11:01:51', NULL, NULL),
(29, 'Network Issue', 'Network connectivity issues in the main office.', 'Open', 'Medium', 'Problèmes Techniques', 26, NULL, '2025-01-17 11:02:25', NULL, NULL),
(30, 'Problème d\'installation du logiciel', 'Le logiciel ne s\'installe pas correctement sur les machines de l\'équipe.', 'Open', 'High', 'Problèmes Techniques', 26, NULL, '2025-01-17 11:08:10', NULL, NULL),
(31, 'Baisse de la performance des applications', 'Les applications internes deviennent lentement réactives en raison d\'une charge de travail élevée.', 'Open', 'Medium', 'Problèmes de Performance', 26, NULL, '2025-01-17 11:08:44', NULL, NULL),
(32, 'Account Access', 'Details', 'Resolved', 'High', 'Problèmes Employés', 26, 34, '2025-01-17 13:44:02', '2025-01-17 13:45:39', 'Nice Work'),
(33, 'Réduction de la performance du serveur', 'Le serveur montre des signes de ralentissement en raison de charges excessives.', 'Resolved', 'Low', 'Problèmes de Performance', 26, 34, '2025-01-17 14:02:02', '2025-01-17 14:03:11', 'Great Work'),
(34, 'Intrusion dans le réseau', 'Une tentative d\'intrusion dans le réseau de l\'entreprise a été détectée', 'Resolved', 'High', 'Incidents de Sécurité', 26, 34, '2025-01-17 15:24:39', '2025-01-17 15:25:03', 'Professional Work !');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('Employee','Technician','Admin') NOT NULL,
  `department` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id_user`, `username`, `password`, `role`, `department`, `first_name`, `last_name`) VALUES
(5, 'user', 'admin', 'Admin', 'IT', 'Wijdane', 'AASSOUSS'),
(26, 'Wijdane', '123', 'Employee', 'Marketing', 'Wijdane', 'Wijdane'),
(27, 'Samia', '', 'Employee', 'Finance', 'Samia', 'Lkarim'),
(29, 'Mima', '12', 'Employee', 'IT', 'Mima', 'Mimo'),
(30, 'Mima', '12', 'Employee', 'Finance', 'Mima889', 'Mimo'),
(31, 'Malak', '1234', 'Employee', 'Finance', 'Malak', 'Lkarim'),
(32, 'Amina', '', 'Employee', 'Finance', 'Amina', 'Lkarim'),
(34, 'Amal', '12', 'Technician', 'Finance', 'Amal', 'Amal'),
(35, 'Lakrim', '123', 'Technician', 'IT', 'Jihan', 'Jihan'),
(37, 'Iman', '123', 'Employee', 'IT', 'Iman', 'aassouss'),
(38, 'Iman', '123', 'Employee', 'IT', 'Iman', 'aassouss');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `incident`
--
ALTER TABLE `incident`
  ADD PRIMARY KEY (`incident_id`),
  ADD KEY `created_by` (`created_by`),
  ADD KEY `assigned_to` (`assigned_to`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `incident`
--
ALTER TABLE `incident`
  MODIFY `incident_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `incident`
--
ALTER TABLE `incident`
  ADD CONSTRAINT `incident_ibfk_1` FOREIGN KEY (`created_by`) REFERENCES `user` (`id_user`) ON DELETE CASCADE,
  ADD CONSTRAINT `incident_ibfk_2` FOREIGN KEY (`assigned_to`) REFERENCES `user` (`id_user`) ON DELETE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
