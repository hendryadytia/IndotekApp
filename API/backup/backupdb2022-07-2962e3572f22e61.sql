#
# TABLE STRUCTURE FOR: administrator
#

DROP TABLE IF EXISTS `administrator`;

CREATE TABLE `administrator` (
  `id` varchar(20) NOT NULL,
  `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) NOT NULL,
  `nama` varchar(30) NOT NULL,
  `jabatan` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `jabatan` (`jabatan`)
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `administrator` (`id`, `username`, `password`, `nama`, `jabatan`) VALUES ('AD-120722001', 'hendry', '$2y$10$tnxqonVmc36itdyPIFqQoOmULu.JOhkAXFF3ykuoZkCbxum8.eHd6', 'hendryadytia', 1);
INSERT INTO `administrator` (`id`, `username`, `password`, `nama`, `jabatan`) VALUES ('AD-120722002', 'admin1', '$2y$10$VB/W/g7TjXtT2OLEs.bYGuVkwzP1PsVQzw26ehMMjU72MeYXCfTC6', 'Admin ISM', 2);
INSERT INTO `administrator` (`id`, `username`, `password`, `nama`, `jabatan`) VALUES ('AD-160722001', 'duaduaduaaa', '$2y$10$.mtB7fAJYOHRNnvLwtS.huv/pGdSTT8UE6FKSHwxNz7fIcA9jTyFa', 'admin2', 2);
INSERT INTO `administrator` (`id`, `username`, `password`, `nama`, `jabatan`) VALUES ('AD-160722002', '23423', '$2y$10$BqejmKkB9StFFjhH74bosuzGpiFlRAC.Lfw0rxvEjxFj6RT5a3.t.', 'test', 2);
INSERT INTO `administrator` (`id`, `username`, `password`, `nama`, `jabatan`) VALUES ('AD-200722001', 'admin2', '$2y$10$gKzLLaDNqu7dD.twDqGUROSQzvuo4NKvw4qAviUi.RY4E9o4BY9g.', 'admin tset2', 2);


#
# TABLE STRUCTURE FOR: jabatan
#

DROP TABLE IF EXISTS `jabatan`;

CREATE TABLE `jabatan` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nama_jabatan` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `jabatan` (`id`, `nama_jabatan`) VALUES (1, 'Manager');
INSERT INTO `jabatan` (`id`, `nama_jabatan`) VALUES (2, 'Admin');


#
# TABLE STRUCTURE FOR: log_akun
#

DROP TABLE IF EXISTS `log_akun`;

CREATE TABLE `log_akun` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_akun` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tgl_dibuat` date DEFAULT NULL,
  `tgl_update` date DEFAULT NULL,
  `tgl_login` date DEFAULT NULL,
  `ip_address` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_akun` (`id_akun`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (1, 'AD-120722001', NULL, NULL, '2022-07-27', '127.0.0.1');
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (2, 'TK-270722001', '2022-07-27', NULL, NULL, NULL);
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (3, 'AD-120722001', NULL, NULL, '2022-07-27', '127.0.0.1');
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (4, 'TK-270722001', '2022-07-27', NULL, NULL, NULL);
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (5, 'AD-120722001', NULL, NULL, '2022-07-27', '127.0.0.1');
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (6, 'TK-270722001', '2022-07-27', NULL, NULL, NULL);
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (7, 'AD-120722001', NULL, NULL, '2022-07-27', '127.0.0.1');
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (8, 'AD-120722001', NULL, NULL, '2022-07-27', '127.0.0.1');
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (9, 'AD-120722001', NULL, NULL, '2022-07-27', '127.0.0.1');
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (10, 'TK-270722001', '2022-07-27', NULL, NULL, NULL);
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (11, 'AD-120722001', NULL, NULL, '2022-07-27', '127.0.0.1');
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (12, 'AD-120722001', NULL, NULL, '2022-07-27', '127.0.0.1');
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (13, 'TK-270722002', '2022-07-27', NULL, NULL, NULL);
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (14, 'AD-120722001', NULL, NULL, '2022-07-27', '127.0.0.1');
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (15, 'AD-120722001', NULL, NULL, '2022-07-27', '127.0.0.1');
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (16, 'AD-120722001', NULL, NULL, '2022-07-27', '127.0.0.1');
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (17, 'AD-120722001', NULL, NULL, '2022-07-27', '127.0.0.1');
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (18, 'AD-120722001', NULL, NULL, '2022-07-27', '127.0.0.1');
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (19, 'AD-120722001', NULL, NULL, '2022-07-29', '127.0.0.1');
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (20, 'AD-120722001', NULL, NULL, '2022-07-29', '127.0.0.1');
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (21, 'AD-120722001', NULL, NULL, '2022-07-29', '127.0.0.1');
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (22, 'AD-120722001', NULL, NULL, '2022-07-29', '127.0.0.1');
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (23, 'AD-120722001', NULL, NULL, '2022-07-29', '127.0.0.1');
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (24, 'TK-270722002', NULL, '2022-07-29', NULL, NULL);
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (25, 'TK-270722002', NULL, NULL, '2022-07-29', 'UNKNOWN');
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (26, 'AD-120722001', NULL, NULL, '2022-07-29', 'UNKNOWN');
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (27, 'TK-270722002', NULL, NULL, '2022-07-29', 'UNKNOWN');
INSERT INTO `log_akun` (`id`, `id_akun`, `tgl_dibuat`, `tgl_update`, `tgl_login`, `ip_address`) VALUES (28, 'AD-120722001', NULL, NULL, '2022-07-29', '127.0.0.1');


#
# TABLE STRUCTURE FOR: log_backup
#

DROP TABLE IF EXISTS `log_backup`;

CREATE TABLE `log_backup` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tanggal` date DEFAULT NULL,
  `nama_file` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `log_backup` (`id`, `tanggal`, `nama_file`) VALUES (1, '2022-07-11', '2022-07-1162cc61075f683.gz');
INSERT INTO `log_backup` (`id`, `tanggal`, `nama_file`) VALUES (2, '2022-07-16', '2022-07-1662d2635ff0356.gz');
INSERT INTO `log_backup` (`id`, `tanggal`, `nama_file`) VALUES (3, '2022-07-16', '2022-07-1662d26794e28fc.gz');
INSERT INTO `log_backup` (`id`, `tanggal`, `nama_file`) VALUES (4, '2022-07-16', '2022-07-1662d267ad9fde5.gz');
INSERT INTO `log_backup` (`id`, `tanggal`, `nama_file`) VALUES (5, '2022-07-16', '2022-07-1662d268094a7f5.gz');


#
# TABLE STRUCTURE FOR: mesin
#

DROP TABLE IF EXISTS `mesin`;

CREATE TABLE `mesin` (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `jenis_mesin` varchar(50) DEFAULT NULL,
  `merek` text NOT NULL,
  `keterangan` text NOT NULL,
  `foto_after` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `foto_before` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` int DEFAULT NULL,
  `tgl_selesai` date DEFAULT NULL,
  `tgl_masuk` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `mesin` (`id`, `jenis_mesin`, `merek`, `keterangan`, `foto_after`, `foto_before`, `status`, `tgl_selesai`, `tgl_masuk`) VALUES ('MS-270722002', 'asf22er1', 'wed', 'aefwef', NULL, NULL, 0, NULL, '2022-07-27');
INSERT INTO `mesin` (`id`, `jenis_mesin`, `merek`, `keterangan`, `foto_after`, `foto_before`, `status`, `tgl_selesai`, `tgl_masuk`) VALUES ('MS-270722003', 'awefw', 'awefw', 'afwaef', NULL, NULL, 0, NULL, '2022-07-27');
INSERT INTO `mesin` (`id`, `jenis_mesin`, `merek`, `keterangan`, `foto_after`, `foto_before`, `status`, `tgl_selesai`, `tgl_masuk`) VALUES ('MS-270722004', '1', 'qw3rq', 'aerfw', NULL, NULL, 0, NULL, '2022-07-27');
INSERT INTO `mesin` (`id`, `jenis_mesin`, `merek`, `keterangan`, `foto_after`, `foto_before`, `status`, `tgl_selesai`, `tgl_masuk`) VALUES ('MS-270722005', 'aweta', 'awet', 'awf aw', NULL, NULL, 0, NULL, '2022-07-27');
INSERT INTO `mesin` (`id`, `jenis_mesin`, `merek`, `keterangan`, `foto_after`, `foto_before`, `status`, `tgl_selesai`, `tgl_masuk`) VALUES ('MS-270722006', 'afw', 'awf', 'fawef', NULL, NULL, 0, NULL, '2022-07-27');
INSERT INTO `mesin` (`id`, `jenis_mesin`, `merek`, `keterangan`, `foto_after`, `foto_before`, `status`, `tgl_selesai`, `tgl_masuk`) VALUES ('MS-290722001', 'blender', 'mixxue', 'tidak mau muter', 'uploads/62e34fce496f5.png', 'uploads/62e353133cbf2.png', 4, '2022-07-29', '2022-07-29');
INSERT INTO `mesin` (`id`, `jenis_mesin`, `merek`, `keterangan`, `foto_after`, `foto_before`, `status`, `tgl_selesai`, `tgl_masuk`) VALUES ('MS-290722002', 'remiove', 'asdf', 'asdfawe', 'uploads/62e356d584faa.png', 'uploads/62e3568b47975.png', 4, '2022-07-29', '2022-07-29');


#
# TABLE STRUCTURE FOR: mesin_diagnosa
#

DROP TABLE IF EXISTS `mesin_diagnosa`;

CREATE TABLE `mesin_diagnosa` (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_mesin` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `id_teknisi` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `diagnosa` text,
  `biaya` varchar(20) DEFAULT NULL,
  `tgl_mulai` date DEFAULT NULL,
  `tgl_pengajuan` date DEFAULT NULL,
  `tgl_acc` date DEFAULT NULL,
  `est_selesai` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_teknisi` (`id_teknisi`),
  KEY `id_mesin` (`id_mesin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `mesin_diagnosa` (`id`, `id_mesin`, `id_teknisi`, `diagnosa`, `biaya`, `tgl_mulai`, `tgl_pengajuan`, `tgl_acc`, `est_selesai`) VALUES ('DG-290722001', 'MS-290722001', 'TK-270722002', 'ganti dinamo', '125000', '2022-07-29', '2022-07-29', '2022-07-29', 2);
INSERT INTO `mesin_diagnosa` (`id`, `id_mesin`, `id_teknisi`, `diagnosa`, `biaya`, `tgl_mulai`, `tgl_pengajuan`, `tgl_acc`, `est_selesai`) VALUES ('DG-290722002', 'MS-290722002', 'TK-270722002', 'remmviow', '335566', '2022-07-29', '2022-07-29', '2022-07-29', 3);


#
# TABLE STRUCTURE FOR: mesin_kepemilikan
#

DROP TABLE IF EXISTS `mesin_kepemilikan`;

CREATE TABLE `mesin_kepemilikan` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_mesin` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `id_teknisi` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `id_pelanggan` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_mesin` (`id_mesin`,`id_teknisi`,`id_pelanggan`),
  KEY `id_teknisi` (`id_teknisi`),
  KEY `id_pelanggan` (`id_pelanggan`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `mesin_kepemilikan` (`id`, `id_mesin`, `id_teknisi`, `id_pelanggan`) VALUES (3, 'MS-270722002', NULL, NULL);
INSERT INTO `mesin_kepemilikan` (`id`, `id_mesin`, `id_teknisi`, `id_pelanggan`) VALUES (4, 'MS-270722003', NULL, NULL);
INSERT INTO `mesin_kepemilikan` (`id`, `id_mesin`, `id_teknisi`, `id_pelanggan`) VALUES (10, 'MS-290722001', 'TK-270722002', 'PG-280722001');
INSERT INTO `mesin_kepemilikan` (`id`, `id_mesin`, `id_teknisi`, `id_pelanggan`) VALUES (11, 'MS-290722002', 'TK-270722002', 'PG-280722001');


#
# TABLE STRUCTURE FOR: mesin_status
#

DROP TABLE IF EXISTS `mesin_status`;

CREATE TABLE `mesin_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nama_status` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `mesin_status` (`id`, `nama_status`) VALUES (0, 'menunggu teknisi');
INSERT INTO `mesin_status` (`id`, `nama_status`) VALUES (1, 'diacc teknisi');
INSERT INTO `mesin_status` (`id`, `nama_status`) VALUES (2, 'menunggu acc admin');
INSERT INTO `mesin_status` (`id`, `nama_status`) VALUES (3, 'diacc admin');
INSERT INTO `mesin_status` (`id`, `nama_status`) VALUES (4, 'selesai');
INSERT INTO `mesin_status` (`id`, `nama_status`) VALUES (5, 'retur');


#
# TABLE STRUCTURE FOR: pelanggan
#

DROP TABLE IF EXISTS `pelanggan`;

CREATE TABLE `pelanggan` (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `nama` varchar(30) NOT NULL,
  `alamat` text NOT NULL,
  `no_telp` varchar(14) NOT NULL,
  `tgl_dibuat` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `pelanggan` (`id`, `nama`, `alamat`, `no_telp`, `tgl_dibuat`) VALUES ('PG-280722001', 'fadillah', 'jl raya kebon jeruk', '09909231', '2022-07-28');


#
# TABLE STRUCTURE FOR: teknisi
#

DROP TABLE IF EXISTS `teknisi`;

CREATE TABLE `teknisi` (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(30) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nama` varchar(30) DEFAULT NULL,
  `alamat` text,
  `no_telp` varchar(14) DEFAULT NULL,
  `keahlian` text,
  `foto_diri` varchar(50) NOT NULL,
  `foto_workshop` varchar(50) NOT NULL,
  `jml_diproses` int DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `teknisi` (`id`, `username`, `password`, `nama`, `alamat`, `no_telp`, `keahlian`, `foto_diri`, `foto_workshop`, `jml_diproses`) VALUES ('TK-270722002', 'awef', '$2y$10$kBz6tYvEuzFpy3XyRIfWmuCB9HmD.pgcVLgccgApgCJpXHp6GUnKa', 'asfw', '23r', '3r2', 'awr', 'uploads/62e353278c144.png', 'uploads/62e3532fef639.png', 2);


