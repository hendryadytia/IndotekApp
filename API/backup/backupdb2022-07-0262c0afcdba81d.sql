#
# TABLE STRUCTURE FOR: administrator
#

DROP TABLE IF EXISTS `administrator`;

CREATE TABLE `administrator` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(14) NOT NULL,
  `nama` varchar(30) NOT NULL,
  `password` varchar(250) NOT NULL,
  `tgl_bergabung` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `tgl_dibuat` varchar(16) NOT NULL,
  `tgl_diubah` varchar(16) NOT NULL,
  `jabatan` varchar(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `administrator` (`id`, `username`, `nama`, `password`, `tgl_bergabung`, `tgl_dibuat`, `tgl_diubah`, `jabatan`) VALUES (8, 'umami', 'kowalski', '$2y$10$cRKsxvXb6KF/8dKDRmC8pOH11px2pmPgknzhPB6qkG9J1i49mU6t.', '2112', '', '19-06-2022', '1');
INSERT INTO `administrator` (`id`, `username`, `nama`, `password`, `tgl_bergabung`, `tgl_dibuat`, `tgl_diubah`, `jabatan`) VALUES (24, '123123', 'uwaw', '$2y$10$uq.KC01ww/YxYYVeUjSQYOt9bTIyKNBjG6ZQVhoS./LOSiR57gbWi', '21-06-2022', '18-06-2022', '2022-07-02', '2');


#
# TABLE STRUCTURE FOR: administrator_jabatan
#

DROP TABLE IF EXISTS `administrator_jabatan`;

CREATE TABLE `administrator_jabatan` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nama` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `administrator_jabatan` (`id`, `nama`) VALUES (1, 'Manager');
INSERT INTO `administrator_jabatan` (`id`, `nama`) VALUES (2, 'Admin');


#
# TABLE STRUCTURE FOR: log_backup
#

DROP TABLE IF EXISTS `log_backup`;

CREATE TABLE `log_backup` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tanggal` varchar(14) NOT NULL,
  `nama_file` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `log_backup` (`id`, `tanggal`, `nama_file`) VALUES (1, '2022-07-02', '2022-07-0262c09d393e640.gz');
INSERT INTO `log_backup` (`id`, `tanggal`, `nama_file`) VALUES (2, '2022-07-02', '2022-07-0262c09d9319256.gz');


#
# TABLE STRUCTURE FOR: log_login
#

DROP TABLE IF EXISTS `log_login`;

CREATE TABLE `log_login` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tanggal` varchar(14) NOT NULL,
  `akun` varchar(14) NOT NULL,
  `ip_address` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `log_login` (`id`, `tanggal`, `akun`, `ip_address`) VALUES (1, '2022-07-02', 'umami', '127.0.0.1');
INSERT INTO `log_login` (`id`, `tanggal`, `akun`, `ip_address`) VALUES (2, '2022-07-02', 'udin123', 'UNKNOWN');


#
# TABLE STRUCTURE FOR: mesin
#

DROP TABLE IF EXISTS `mesin`;

CREATE TABLE `mesin` (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `jenis_mesin` varchar(255) NOT NULL,
  `merek` varchar(14) NOT NULL,
  `keluhan` text NOT NULL,
  `tgl_masuk` varchar(14) NOT NULL,
  `tgl_acc_teknisi` varchar(14) NOT NULL,
  `tgl_batal` varchar(14) NOT NULL,
  `tgl_update` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `tgl_deadline` varchar(14) NOT NULL,
  `tgl_selesai` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `est_selesai` varchar(1) NOT NULL,
  `foto_before` varchar(255) NOT NULL,
  `foto_after` varchar(255) NOT NULL,
  `foto_sparepart` varchar(255) NOT NULL,
  `status` varchar(10) NOT NULL,
  `diagnosa` text NOT NULL,
  `biaya` varchar(14) NOT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `mesin` (`id`, `jenis_mesin`, `merek`, `keluhan`, `tgl_masuk`, `tgl_acc_teknisi`, `tgl_batal`, `tgl_update`, `tgl_deadline`, `tgl_selesai`, `est_selesai`, `foto_before`, `foto_after`, `foto_sparepart`, `status`, `diagnosa`, `biaya`) VALUES ('MSN-62c0acc291128', 'asdfkjdf', 'aksjdas', 'gkjhgkjhg', '2022-07-02', '2022-07-02', '', '2022-07-02', '2022-7-1', '', '2', '', '', '', '3', 'test', '2000');


#
# TABLE STRUCTURE FOR: mesin_kepemilikan
#

DROP TABLE IF EXISTS `mesin_kepemilikan`;

CREATE TABLE `mesin_kepemilikan` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_mesin` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_pelanggan` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_teknisi` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `mesin_kepemilikan` (`id`, `id_mesin`, `id_pelanggan`, `id_teknisi`) VALUES (1, 'MSN-62c0acc291128', '4aa9e975-ef5d-4e03-ac21-cfa6b704d067', 'TK-62c0aab01e794');


#
# TABLE STRUCTURE FOR: mesin_status
#

DROP TABLE IF EXISTS `mesin_status`;

CREATE TABLE `mesin_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `mesin_status` (`id`, `status`) VALUES (0, 'menunggu');
INSERT INTO `mesin_status` (`id`, `status`) VALUES (1, 'accteknisi');
INSERT INTO `mesin_status` (`id`, `status`) VALUES (2, 'accharga');
INSERT INTO `mesin_status` (`id`, `status`) VALUES (3, 'proses');
INSERT INTO `mesin_status` (`id`, `status`) VALUES (4, 'selesai');
INSERT INTO `mesin_status` (`id`, `status`) VALUES (5, 'retur');


#
# TABLE STRUCTURE FOR: notifikasi
#

DROP TABLE IF EXISTS `notifikasi`;

CREATE TABLE `notifikasi` (
  `id` int NOT NULL AUTO_INCREMENT,
  `jenis` varchar(14) NOT NULL,
  `tanggal` varchar(14) NOT NULL,
  `isi` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `id_mesin` varchar(20) NOT NULL,
  `id_tertuju` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `notifikasi` (`id`, `jenis`, `tanggal`, `isi`, `id_mesin`, `id_tertuju`) VALUES (1, 'orderan baru', '2022-07-02', 'Orderan Perbaiki Mesin', 'MSN-62c0acc291128', 'TK-62c0aab01e794');


#
# TABLE STRUCTURE FOR: pelanggan
#

DROP TABLE IF EXISTS `pelanggan`;

CREATE TABLE `pelanggan` (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `nama` varchar(30) NOT NULL,
  `alamat` varchar(255) NOT NULL,
  `no_telp` varchar(14) NOT NULL,
  `tgl_dibuat` varchar(14) NOT NULL,
  `tgl_diubah` varchar(14) NOT NULL,
  `jml_diproses` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `pelanggan` (`id`, `nama`, `alamat`, `no_telp`, `tgl_dibuat`, `tgl_diubah`, `jml_diproses`) VALUES ('4aa9e975-ef5d-4e03-ac21-cfa6b704d067', 'erwan', 'jl wibawa 2', '089506500371', '2022-07-02', '', '');


#
# TABLE STRUCTURE FOR: teknisi
#

DROP TABLE IF EXISTS `teknisi`;

CREATE TABLE `teknisi` (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(30) NOT NULL,
  `nama` varchar(30) NOT NULL,
  `password` varchar(255) NOT NULL,
  `tgl_dibuat` varchar(14) NOT NULL,
  `tgl_diubah` varchar(14) NOT NULL,
  `foto_diri` varchar(50) NOT NULL,
  `foto_workshop` varchar(50) NOT NULL,
  `alamat` text NOT NULL,
  `keahlian` text NOT NULL,
  `no_telp` varchar(16) NOT NULL,
  `jml_diproses` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `teknisi` (`id`, `username`, `nama`, `password`, `tgl_dibuat`, `tgl_diubah`, `foto_diri`, `foto_workshop`, `alamat`, `keahlian`, `no_telp`, `jml_diproses`) VALUES ('TK-62c0aab01e794', 'udin123', 'Udin', '$2y$10$/ZubllQNVVvh48wEVn801O1HmpKyX6tzKmec4de.UcM07UwzhAtBG', '2022-07-02', '2022-07-02', '', '', 'jl wibawa 2', 'kipas angin,mesin cuci', '089506500371', '1');
INSERT INTO `teknisi` (`id`, `username`, `nama`, `password`, `tgl_dibuat`, `tgl_diubah`, `foto_diri`, `foto_workshop`, `alamat`, `keahlian`, `no_telp`, `jml_diproses`) VALUES ('TK-62c0ab69b757f', 'elan123', 'Elan', '$2y$10$Rk3d7QPeP2RV/NLnM1H8AOSPhK/p50mmMu/YLiNmUB5PlDmTBGkoK', '2022-07-02', '2022-07-02', '', '', 'limo2', 'TV', '9999999', '0');


