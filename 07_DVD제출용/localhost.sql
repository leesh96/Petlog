-- phpMyAdmin SQL Dump
-- version 4.6.6deb5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- 생성 시간: 20-06-20 00:48
-- 서버 버전: 5.7.30-0ubuntu0.18.04.1
-- PHP 버전: 7.2.24-0ubuntu0.18.04.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 데이터베이스: `petlog`
--
CREATE DATABASE IF NOT EXISTS `petlog` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `petlog`;

-- --------------------------------------------------------

--
-- 테이블 구조 `diary`
--

CREATE TABLE `diary` (
  `diary_id` int(10) NOT NULL,
  `diary_owner` varchar(20) NOT NULL,
  `diary_title` varchar(50) NOT NULL,
  `diary_contents` text NOT NULL,
  `diary_media` varchar(255) DEFAULT NULL,
  `diary_mood` tinyint(4) NOT NULL,
  `diary_writedate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `diary`
--

INSERT INTO `diary` (`diary_id`, `diary_owner`, `diary_title`, `diary_contents`, `diary_media`, `diary_mood`, `diary_writedate`) VALUES
(1, 'cksdnd9809@naver.com', '오랜만에 산책!', '시험기간이 끝나고 우리 강아지랑 산책을 했다.', 'mypetimg/20200614_034818', 0, '2020-06-14'),
(2, 'lksy1294@naver.com', '남강 산책', '강아지와 함께 남강 산책을 하고 왔다.\n오랜만에 한 산책이라 매우 신나보였다.', 'diaryimg/20200614_02595020200614.jpg', 0, '2020-05-13'),
(3, 'leedg1569@naver.com', '오늘의 펫스타그램', '강아지가 오늘따라 너무 조용히 있다...\n#무표정 #배고프니..? #무념무상', 'diaryimg/20200619_123919804219541_1592051895.jpeg', 4, '2020-06-19'),
(4, 'bydo2@gmail.com', '사마귀', '집앞에 사마귀가 찾아왔다', 'diaryimg/20200619_05104120200614_202350.jpg', 4, '2020-06-19'),
(5, 'bydo2@gmail.com', '사마귀', '집앞에 사마귀가 찾아왔다', 'diaryimg/20200619_05110320200614_202350.jpg', 4, '2020-06-19'),
(6, 'bydo2@gmail.com', '사마귀', '집앞에 사마귀가 찾아왔다', 'diaryimg/20200619_05110320200614_202350.jpg', 4, '2020-06-19'),
(7, 'bydo2@gmail.com', '사마귀', '집앞에 사마귀가 찾아왔다', 'diaryimg/20200619_05110320200614_202350.jpg', 4, '2020-06-19'),
(8, 'bydo2@gmail.com', '사마귀', '집앞에 사마귀가 찾아왔다', 'diaryimg/20200619_05110420200614_202350.jpg', 4, '2020-06-19'),
(9, 'bydo2@gmail.com', '사마귀', '집앞에 사마귀가 찾아왔다', 'diaryimg/20200619_05110420200614_202350.jpg', 4, '2020-06-19'),
(10, 'bydo2@gmail.com', '사마귀', '집앞에 사마귀가 찾아왔다', 'diaryimg/20200619_05111820200614_202350.jpg', 4, '2020-06-19'),
(11, 'bydo2@gmail.com', '사마귀', '집앞에 사마귀가 찾아왔다', 'diaryimg/20200619_05114320200614_202350.jpg', 4, '2020-06-19'),
(12, 'bydo2@gmail.com', '사마귀', '집앞에 사마귀가 찾아왔다', 'diaryimg/20200619_05115020200614_202350.jpg', 4, '2020-06-19'),
(13, 'bydo2@gmail.com', '사마귀', '집앞에 사마귀가 찾아왔다', 'diaryimg/20200619_05120620200614_202350.jpg', 4, '2020-06-19'),
(14, 'bydo2@gmail.com', '사마귀', '집앞에 사마귀가 찾아왔다', 'diaryimg/20200619_05120820200614_202350.jpg', 4, '2020-06-19'),
(15, 'bydo2@gmail.com', '사마귀', '.', 'diaryimg/20200619_05125720200614_202350.jpg', 4, '2020-06-19'),
(16, 'bydo2@gmail.com', '사마귀', '.', 'diaryimg/20200619_05130020200614_202350.jpg', 4, '2020-06-19'),
(17, 'bydo2@gmail.com', '사마귀', '.', 'diaryimg/20200619_05131720200614_202350.jpg', 4, '2020-06-19'),
(18, 'bydo2@gmail.com', '사마귀', '.', 'diaryimg/20200619_05133020200614_202350.jpg', 4, '2020-06-19'),
(19, 'bydo2@gmail.com', '사마귀', '.', 'diaryimg/20200619_05134720200614_202350.jpg', 4, '2020-06-19'),
(20, 'bydo2@gmail.com', '사마귀', '.', 'diaryimg/20200619_05135020200614_202350.jpg', 4, '2020-06-19'),
(21, 'bydo2@gmail.com', '사마귀', '.', 'diaryimg/20200619_05135120200614_202350.jpg', 4, '2020-06-19'),
(22, 'bydo2@gmail.com', '사마귀', '.', 'diaryimg/20200619_05135220200614_202350.jpg', 4, '2020-06-19'),
(23, 'bydo2@gmail.com', '사마귀', '.', 'diaryimg/20200619_05135320200614_202350.jpg', 4, '2020-06-19'),
(24, 'bydo2@gmail.com', '사마귀', '.', 'diaryimg/20200619_05140420200614_202350.jpg', 4, '2020-06-19'),
(25, 'bydo2@gmail.com', '사마귀', '집앞에 사마귀가 찾아왔다', 'diaryimg/20200619_05140520200614_202350.jpg', 4, '2020-06-19'),
(26, 'bydo2@gmail.com', '사마귀', '.', 'diaryimg/20200619_05141520200614_202350.jpg', 4, '2020-06-19'),
(27, 'bydo2@gmail.com', '사마귀', '.', 'diaryimg/20200619_05142620200614_202350.jpg', 4, '2020-06-19'),
(28, 'bydo2@gmail.com', '사마귀', '.', 'diaryimg/20200619_05142720200614_202350.jpg', 4, '2020-06-19'),
(29, 'bydo2@gmail.com', '사마귀', '.', 'diaryimg/20200619_05142720200614_202350.jpg', 4, '2020-06-19'),
(30, 'bydo2@gmail.com', '사마귀', '.', 'diaryimg/20200619_05142820200614_202350.jpg', 4, '2020-06-19'),
(31, 'bydo2@gmail.com', '사마귀', '.', 'diaryimg/20200619_05142820200614_202350.jpg', 4, '2020-06-19'),
(32, 'bydo2@gmail.com', '사마귀', '집앞에 사마귀가 찾아왔다', 'diaryimg/20200619_05143220200614_202350.jpg', 4, '2020-06-19'),
(33, 'bydo2@gmail.com', '사마귀', '집앞에 사마귀가 찾아왔다', 'diaryimg/20200619_05143220200614_202350.jpg', 4, '2020-06-19'),
(34, 'bydo2@gmail.com', '사마귀', '.', 'diaryimg/20200619_05143220200614_202350.jpg', 4, '2020-06-19'),
(35, 'bydo2@gmail.com', '사마귀', '.', 'diaryimg/20200619_05143420200614_202350.jpg', 4, '2020-06-19'),
(36, 'wodud2970@nate.com', '재영', '재영', 'diaryimg/20200619_07285820200615_221112.jpg', 0, '2020-06-19'),
(37, 'wowogur12@naver.com', '옙', 'ㅇㅇㅇㅇ.', 'diaryimg/20200619_07573420200619_195609.jpg', 0, '2020-06-19'),
(38, 'wowogur12@naver.com', '옙', 'ㅇㅇㅇㅇ.', 'diaryimg/20200619_07573620200619_195609.jpg', 0, '2020-06-19'),
(41, 'lksy1294@naver.com', 'test', 'test', 'diaryimg/20200619_084720IMG_20200616_064516.jpg', 0, '2020-06-19'),
(42, 'hyunjia2@naver.com', '헿헥헥', '행복해 보인다', 'diaryimg/20200619_085341dog-panting.jpg', 0, '2020-06-19'),
(43, 'hyunjia2@naver.com', '궁뎅이', '빵실한 엉덩이', 'diaryimg/20200619_092745z05iby35955xer75h8x4.jpg', 0, '2020-06-19');

-- --------------------------------------------------------

--
-- 테이블 구조 `follow`
--

CREATE TABLE `follow` (
  `follow_id` int(11) NOT NULL,
  `user_nickname` varchar(20) NOT NULL,
  `follower_nickname` varchar(20) NOT NULL,
  `isfollow` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `follow`
--

INSERT INTO `follow` (`follow_id`, `user_nickname`, `follower_nickname`, `isfollow`) VALUES
(3, '한찬웅', '이수호', 0),
(5, '이수호', '최동민', 1),
(6, '최동민', '한찬웅', 1),
(7, '최동민', '이수호', 1),
(8, '도몽', 'Variable', 0),
(9, '서현지', '이수호', 1);

-- --------------------------------------------------------

--
-- 테이블 구조 `mypet_info`
--

CREATE TABLE `mypet_info` (
  `pet_id` int(10) NOT NULL,
  `pet_owner` varchar(20) DEFAULT NULL,
  `pet_name` varchar(20) NOT NULL,
  `pet_sex` varchar(20) NOT NULL,
  `pet_species` varchar(20) NOT NULL,
  `pet_age` varchar(20) NOT NULL,
  `pet_bday` date NOT NULL,
  `pet_memo` text,
  `pet_face` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `mypet_info`
--

INSERT INTO `mypet_info` (`pet_id`, `pet_owner`, `pet_name`, `pet_sex`, `pet_species`, `pet_age`, `pet_bday`, `pet_memo`, `pet_face`) VALUES
(1, 'cksdnd9809@naver.com', '포메', '남', '포메라니안', '3', '2017-05-06', '주말에 병원 데리고 가야함', 'mypetimg/20200614_025212cksdnd9809@naver.com강아지 사진.jpg'),
(2, 'lksy1294@naver.com', '강아지', '남자', '비숑', '2살', '2018-11-30', '토요일 동물병원 예약 2시', 'mypetimg/20200615_114411lksy1294@naver.comdiary223.jpg'),
(10, 'bydo2@gmail.com', '사마귀', '?', '사마귀', '1시간', '2020-06-19', NULL, 'mypetimg/20200619_020726bydo2@gmail.com20200614_202350.jpg'),
(11, 'bydo2@gmail.com', '사마귀', '?', '사마귀', '1시간', '2020-06-19', NULL, 'mypetimg/20200619_020728bydo2@gmail.com20200614_202350.jpg'),
(12, 'bydo2@gmail.com', '사마귀', '?', '사마귀', '1시간', '2020-06-19', NULL, 'mypetimg/20200619_020728bydo2@gmail.com20200614_202350.jpg'),
(13, 'bydo2@gmail.com', '사마귀', '?', '사마귀', '1시간', '2020-06-19', NULL, 'mypetimg/20200619_020728bydo2@gmail.com20200614_202350.jpg'),
(14, 'leedg1569@naver.com', '몽뭉이', '남', '요크셔테리어', '5', '2020-06-19', NULL, 'mypetimg/20200619_124138leedg1569@naver.com'),
(15, 'wodud2970@nate.com', '깐풍기', '중', '깐풍기', '1시간', '2020-06-17', NULL, 'mypetimg/20200619_073808wodud2970@nate.com20200615_221112.jpg'),
(16, 'wowogur12@naver.com', '흐엥이', '남', '이어폰', '1', '2020-06-19', NULL, 'mypetimg/20200619_075817wowogur12@naver.com20200619_195609.jpg'),
(17, 'hyunjia2@naver.com', '땡이', '남자', '웰시코기', '6', '2014-10-01', '건들지 마셈 건들면 뭄', 'mypetimg/20200619_085632hyunjia2@naver.comdog-panting.jpg'),
(18, 'hyunjia2@naver.com', '.', '.', '.', '.', '2020-06-19', NULL, 'mypetimg/20200619_094813hyunjia2@naver.comz05iby35955xer75h8x4.jpg');

-- --------------------------------------------------------

--
-- 테이블 구조 `notice`
--

CREATE TABLE `notice` (
  `notice_id` int(11) NOT NULL,
  `notice_title` varchar(255) NOT NULL,
  `notice_content` text NOT NULL,
  `notice_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `notice`
--

INSERT INTO `notice` (`notice_id`, `notice_title`, `notice_content`, `notice_date`) VALUES
(1, 'PetLog 앱이 출시되었습니다.', '반려동물인들을 위한 최적화된 앱 PetLog\r\n지금 바로 플레이스토어에서 만나보세요.', '2020-06-12'),
(2, '2020.06.15 업데이트 입니다.', 'PetLog앱이 업데이트 되었습니다.', '2020-06-15');

-- --------------------------------------------------------

--
-- 테이블 구조 `petsta_comments`
--

CREATE TABLE `petsta_comments` (
  `id` int(11) NOT NULL,
  `post_id` int(11) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `nickname` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `petsta_comments`
--

INSERT INTO `petsta_comments` (`id`, `post_id`, `comment`, `nickname`) VALUES
(1, 6, '귀엽네요', '한찬웅'),
(8, 5, '안녕하세요', '한찬웅'),
(13, 23, 'ㄴㄴㄴ\n', '도몽'),
(14, 6, '동민아 시험공부하자 ', 'Variable'),
(15, 25, '흐에', 'zlolz'),
(16, 25, 'ㅋㅋㅋ루삥뽕', '재영'),
(17, 3, 'ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ', '서현지');

-- --------------------------------------------------------

--
-- 테이블 구조 `petsta_like`
--

CREATE TABLE `petsta_like` (
  `id` int(11) NOT NULL,
  `post_id` int(11) NOT NULL,
  `nickname` varchar(20) NOT NULL,
  `islike` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `petsta_like`
--

INSERT INTO `petsta_like` (`id`, `post_id`, `nickname`, `islike`) VALUES
(1, 6, '한찬웅', 0),
(2, 6, '최동민', 1),
(3, 3, '최동민', 1),
(4, 23, '도몽', 0),
(5, 23, '이수호', 1),
(6, 6, 'Variable', 1),
(9, 24, '서현지', 1),
(10, 6, 'heejin', 1);

-- --------------------------------------------------------

--
-- 테이블 구조 `petsta_post`
--

CREATE TABLE `petsta_post` (
  `post_id` int(11) NOT NULL,
  `nickname` varchar(20) NOT NULL,
  `petsta_image` varchar(255) DEFAULT NULL,
  `contents` text NOT NULL,
  `tag` int(8) DEFAULT NULL,
  `write_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `like_cnt` int(20) NOT NULL DEFAULT '0',
  `comment_cnt` int(20) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `petsta_post`
--

INSERT INTO `petsta_post` (`post_id`, `nickname`, `petsta_image`, `contents`, `tag`, `write_time`, `like_cnt`, `comment_cnt`) VALUES
(1, '한찬웅', 'petstaimg/20200614_024832강아지 사진.jpg', '너무 귀엽다 ㅎㅎ', 14, '2020-06-14 05:48:32', 0, 0),
(3, '이수호', 'petstaimg/20200614_03275111_hjj592.jpg', '새로 분양받았어요', 0, '2020-06-14 05:57:28', 1, 1),
(4, '이수호', 'petstaimg/20200614_025802cat-181268_1280.jpg', '잘생겼다 ㅎㅎ', 21, '2020-06-14 05:58:02', 0, 0),
(5, '한찬웅', 'petstaimg/20200614_033019pomedog.jpg', '우리 강아지 어릴때 ^^', 14, '2020-06-14 06:07:18', 0, 1),
(6, '최동민', 'petstaimg/20200614_032054mycat.jpg', '고양이', 22, '2020-06-14 06:20:54', 3, 2),
(22, '한찬웅', 'petstaimg/20200616_0525551581237489938-6.jpg', 'ㄷㄷ', 0, '2020-06-16 08:25:55', 0, 0),
(23, '도몽', 'petstaimg/20200619_02074920200614_202350.jpg', '사마귀', 0, '2020-06-18 17:07:49', 1, 1),
(24, 'Variable', 'petstaimg/20200619_125108IMG_0937.JPG', '#허스키 #카라 #오랜만에 보고싶다', 0, '2020-06-19 03:51:08', 1, 0),
(25, 'zlolz', 'petstaimg/20200619_07563120200619_195609.jpg', 'qcy ~~~', 0, '2020-06-19 10:56:31', 0, 2),
(28, '서현지', 'petstaimg/20200619_093003z05iby35955xer75h8x4.jpg', '궁뎅이 자랑', 0, '2020-06-19 12:30:03', 0, 0);

-- --------------------------------------------------------

--
-- 테이블 구조 `push_setting`
--

CREATE TABLE `push_setting` (
  `id` int(11) NOT NULL,
  `user_nickname` varchar(20) NOT NULL,
  `likeon` tinyint(4) NOT NULL,
  `commenton` tinyint(4) NOT NULL,
  `followon` tinyint(4) NOT NULL,
  `diaryon` tinyint(4) NOT NULL,
  `bdayon` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 테이블 구조 `share_comment`
--

CREATE TABLE `share_comment` (
  `id` int(11) NOT NULL,
  `share_id` int(11) NOT NULL,
  `comment` text NOT NULL,
  `nickname` varchar(20) NOT NULL,
  `title` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `share_comment`
--

INSERT INTO `share_comment` (`id`, `share_id`, `comment`, `nickname`, `title`) VALUES
(2, 2, '카톡아이디 cksdnd9809에요', '한찬웅', '강아지, 고양이 밥그릇 나눠요'),
(3, 3, '얼마인가요??', '한찬웅', '켓타워 나눔합니다');

-- --------------------------------------------------------

--
-- 테이블 구조 `share_post`
--

CREATE TABLE `share_post` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL,
  `nickname` varchar(20) NOT NULL,
  `share_image` varchar(255) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `share_post`
--

INSERT INTO `share_post` (`id`, `title`, `content`, `nickname`, `share_image`, `date`) VALUES
(2, '강아지, 고양이 밥그릇 나눠요', '깨끗하게 관리했습니다. 카톡아이디 남겨주세요.', '한찬웅', 'shareimg/20200614_031539asd.jpg', '2020-06-14 06:15:39'),
(3, '켓타워 나눔합니다', '켓타워를 새로사서 안쓰게된 켓 타워 팔아요.\n댓글로 남겨주세요', '이수호', 'shareimg/20200614_03331110164144_1.jpg', '2020-06-14 06:33:11');

-- --------------------------------------------------------

--
-- 테이블 구조 `user_info`
--

CREATE TABLE `user_info` (
  `u_id` varchar(20) NOT NULL,
  `u_pw` varchar(20) NOT NULL,
  `u_name` varchar(20) NOT NULL,
  `u_nickname` varchar(20) NOT NULL,
  `u_bdy` date NOT NULL,
  `u_intro` varchar(255) DEFAULT NULL,
  `u_face` varchar(255) DEFAULT NULL,
  `follower_cnt` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `user_info`
--

INSERT INTO `user_info` (`u_id`, `u_pw`, `u_name`, `u_nickname`, `u_bdy`, `u_intro`, `u_face`, `follower_cnt`) VALUES
('8072881522@qq.com', 'asd123', 'asdasd', 'yuuy', '2005-06-19', NULL, NULL, 0),
('admin', '1234', '관리자', '관리자', '2020-06-12', NULL, NULL, 0),
('asddad', 'asddad', 'yuyu', 'ppp', '2020-06-19', NULL, NULL, 0),
('bydo2@gmail.com', 'yyee33', '김도연', '도몽', '1996-03-01', NULL, NULL, 0),
('cdm@naver.com', '1', '최동민', '최동민', '2020-06-14', '안녕하세요 ~~', 'profileimg/최동민_mycat.jpg', 1),
('cksdnd9809@naver.com', '1234', '한찬웅', '한찬웅', '1997-03-01', NULL, NULL, 1),
('hyunjia2@naver.com', '0414', '서현지', '서현지', '1999-04-14', '안녕하세요', NULL, 0),
('khj16355@naver.com', '1234', 'heejin', 'heejin', '1999-07-14', NULL, NULL, 0),
('leedg1569@naver.com', '1234', '이대근', 'Variable', '1998-01-01', NULL, NULL, 0),
('lksy1294@naver.com', '123456', '이수호', '이수호', '1996-11-30', '경상대학교 컴퓨터과학과 이수호입니다.', NULL, 2),
('wodud2970@nate.com', '1234', '재영', '재영', '1996-01-06', NULL, NULL, 0),
('wowogur12@naver.com', 'xmflrxj@12', '최재혁', 'zlolz', '1997-01-19', NULL, NULL, 0),
('김민준@naver.com', 'rlaalswns', '김민준', '김민준ㅋ', '2028-06-19', NULL, NULL, 0);

-- --------------------------------------------------------

--
-- 테이블 구조 `walk_post`
--

CREATE TABLE `walk_post` (
  `id` int(11) NOT NULL,
  `title` varchar(30) NOT NULL,
  `content` text NOT NULL,
  `nickname` varchar(20) NOT NULL,
  `walk_image` varchar(255) DEFAULT NULL,
  `walk_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `position` varchar(255) DEFAULT NULL,
  `posx` double DEFAULT NULL COMMENT '위도',
  `posy` double DEFAULT NULL COMMENT '경도'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `walk_post`
--

INSERT INTO `walk_post` (`id`, `title`, `content`, `nickname`, `walk_image`, `walk_date`, `position`, `posx`, `posy`) VALUES
(1, '볼래로입니다', '경상대학교 앞 산책로 볼래로입니다.\r\n봄에는 벚꽃이 예쁘게 펴요.', '관리자', 'walkimg/road.jpg', '2020-06-16 06:10:01', NULL, 35.15394659037594, 128.10606937855482),
(2, '남강입니다', '과기대 옆 남강 산책로 입니다.\r\n반려동물과 뛰어놀기 좋은 곳이에요.', '관리자', 'walkimg/river.jpg', '2020-06-16 06:10:01', NULL, 35.18186027653409, 128.09689924120903),
(3, '석류공원입니다', '간단하게 쉬어갈 수 있는\r\n석류공원 입니다.', '관리자', 'walkimg/park.jpg', '2020-06-16 06:10:01', NULL, 35.166635094772595, 128.10459583997726),
(34, 'ㅇ', 'ㅇㅇ\n', '최동민', 'walkimg/20200616_06194120200616_181352.jpg', '2020-06-16 09:19:41', 'null', 35.15375854079928, 128.10722406953576);

--
-- 덤프된 테이블의 인덱스
--

--
-- 테이블의 인덱스 `diary`
--
ALTER TABLE `diary`
  ADD PRIMARY KEY (`diary_id`),
  ADD KEY `diary_owner` (`diary_owner`);

--
-- 테이블의 인덱스 `follow`
--
ALTER TABLE `follow`
  ADD PRIMARY KEY (`follow_id`),
  ADD KEY `usernick` (`user_nickname`),
  ADD KEY `followernick` (`follower_nickname`);

--
-- 테이블의 인덱스 `mypet_info`
--
ALTER TABLE `mypet_info`
  ADD PRIMARY KEY (`pet_id`),
  ADD KEY `pet_owner` (`pet_owner`);

--
-- 테이블의 인덱스 `notice`
--
ALTER TABLE `notice`
  ADD PRIMARY KEY (`notice_id`);

--
-- 테이블의 인덱스 `petsta_comments`
--
ALTER TABLE `petsta_comments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `comment_post` (`post_id`),
  ADD KEY `comment_nickname` (`nickname`);

--
-- 테이블의 인덱스 `petsta_like`
--
ALTER TABLE `petsta_like`
  ADD PRIMARY KEY (`id`),
  ADD KEY `postid` (`post_id`),
  ADD KEY `liker_nick` (`nickname`);

--
-- 테이블의 인덱스 `petsta_post`
--
ALTER TABLE `petsta_post`
  ADD PRIMARY KEY (`post_id`),
  ADD KEY `petsta_nickname` (`nickname`);

--
-- 테이블의 인덱스 `push_setting`
--
ALTER TABLE `push_setting`
  ADD PRIMARY KEY (`id`),
  ADD KEY `push_nick` (`user_nickname`);

--
-- 테이블의 인덱스 `share_comment`
--
ALTER TABLE `share_comment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `title` (`title`),
  ADD KEY `share_post_id` (`share_id`),
  ADD KEY `share_co_nickname` (`nickname`);

--
-- 테이블의 인덱스 `share_post`
--
ALTER TABLE `share_post`
  ADD PRIMARY KEY (`id`),
  ADD KEY `nickname` (`nickname`),
  ADD KEY `content` (`content`),
  ADD KEY `title` (`title`),
  ADD KEY `id` (`id`);

--
-- 테이블의 인덱스 `user_info`
--
ALTER TABLE `user_info`
  ADD PRIMARY KEY (`u_id`),
  ADD UNIQUE KEY `u_id` (`u_id`),
  ADD UNIQUE KEY `u_id_2` (`u_id`),
  ADD UNIQUE KEY `u_nickname` (`u_nickname`);

--
-- 테이블의 인덱스 `walk_post`
--
ALTER TABLE `walk_post`
  ADD PRIMARY KEY (`id`),
  ADD KEY `writer` (`nickname`),
  ADD KEY `title` (`title`);

--
-- 덤프된 테이블의 AUTO_INCREMENT
--

--
-- 테이블의 AUTO_INCREMENT `diary`
--
ALTER TABLE `diary`
  MODIFY `diary_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;
--
-- 테이블의 AUTO_INCREMENT `follow`
--
ALTER TABLE `follow`
  MODIFY `follow_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- 테이블의 AUTO_INCREMENT `mypet_info`
--
ALTER TABLE `mypet_info`
  MODIFY `pet_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
--
-- 테이블의 AUTO_INCREMENT `notice`
--
ALTER TABLE `notice`
  MODIFY `notice_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- 테이블의 AUTO_INCREMENT `petsta_comments`
--
ALTER TABLE `petsta_comments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- 테이블의 AUTO_INCREMENT `petsta_like`
--
ALTER TABLE `petsta_like`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- 테이블의 AUTO_INCREMENT `petsta_post`
--
ALTER TABLE `petsta_post`
  MODIFY `post_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;
--
-- 테이블의 AUTO_INCREMENT `push_setting`
--
ALTER TABLE `push_setting`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- 테이블의 AUTO_INCREMENT `share_comment`
--
ALTER TABLE `share_comment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
--
-- 테이블의 AUTO_INCREMENT `share_post`
--
ALTER TABLE `share_post`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
--
-- 테이블의 AUTO_INCREMENT `walk_post`
--
ALTER TABLE `walk_post`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;
--
-- 덤프된 테이블의 제약사항
--

--
-- 테이블의 제약사항 `diary`
--
ALTER TABLE `diary`
  ADD CONSTRAINT `diary_owner` FOREIGN KEY (`diary_owner`) REFERENCES `user_info` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 테이블의 제약사항 `follow`
--
ALTER TABLE `follow`
  ADD CONSTRAINT `followernick` FOREIGN KEY (`follower_nickname`) REFERENCES `user_info` (`u_nickname`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `usernick` FOREIGN KEY (`user_nickname`) REFERENCES `user_info` (`u_nickname`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 테이블의 제약사항 `mypet_info`
--
ALTER TABLE `mypet_info`
  ADD CONSTRAINT `pet_owner` FOREIGN KEY (`pet_owner`) REFERENCES `user_info` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 테이블의 제약사항 `petsta_comments`
--
ALTER TABLE `petsta_comments`
  ADD CONSTRAINT `comment_nickname` FOREIGN KEY (`nickname`) REFERENCES `user_info` (`u_nickname`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `comment_post` FOREIGN KEY (`post_id`) REFERENCES `petsta_post` (`post_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 테이블의 제약사항 `petsta_like`
--
ALTER TABLE `petsta_like`
  ADD CONSTRAINT `liker_nick` FOREIGN KEY (`nickname`) REFERENCES `user_info` (`u_nickname`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `postid` FOREIGN KEY (`post_id`) REFERENCES `petsta_post` (`post_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 테이블의 제약사항 `petsta_post`
--
ALTER TABLE `petsta_post`
  ADD CONSTRAINT `petsta_nickname` FOREIGN KEY (`nickname`) REFERENCES `user_info` (`u_nickname`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 테이블의 제약사항 `push_setting`
--
ALTER TABLE `push_setting`
  ADD CONSTRAINT `push_nick` FOREIGN KEY (`user_nickname`) REFERENCES `user_info` (`u_nickname`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 테이블의 제약사항 `share_comment`
--
ALTER TABLE `share_comment`
  ADD CONSTRAINT `share_co_nickname` FOREIGN KEY (`nickname`) REFERENCES `user_info` (`u_nickname`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `share_post_id` FOREIGN KEY (`share_id`) REFERENCES `share_post` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `title` FOREIGN KEY (`title`) REFERENCES `share_post` (`title`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 테이블의 제약사항 `share_post`
--
ALTER TABLE `share_post`
  ADD CONSTRAINT `nickname` FOREIGN KEY (`nickname`) REFERENCES `user_info` (`u_nickname`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 테이블의 제약사항 `walk_post`
--
ALTER TABLE `walk_post`
  ADD CONSTRAINT `writer` FOREIGN KEY (`nickname`) REFERENCES `user_info` (`u_nickname`) ON DELETE CASCADE ON UPDATE CASCADE;
--
-- 데이터베이스: `phpmyadmin`
--
CREATE DATABASE IF NOT EXISTS `phpmyadmin` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `phpmyadmin`;

-- --------------------------------------------------------

--
-- 테이블 구조 `pma__bookmark`
--

CREATE TABLE `pma__bookmark` (
  `id` int(11) NOT NULL,
  `dbase` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `user` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `label` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `query` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Bookmarks';

-- --------------------------------------------------------

--
-- 테이블 구조 `pma__central_columns`
--

CREATE TABLE `pma__central_columns` (
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `col_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `col_type` varchar(64) COLLATE utf8_bin NOT NULL,
  `col_length` text COLLATE utf8_bin,
  `col_collation` varchar(64) COLLATE utf8_bin NOT NULL,
  `col_isNull` tinyint(1) NOT NULL,
  `col_extra` varchar(255) COLLATE utf8_bin DEFAULT '',
  `col_default` text COLLATE utf8_bin
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Central list of columns';

-- --------------------------------------------------------

--
-- 테이블 구조 `pma__column_info`
--

CREATE TABLE `pma__column_info` (
  `id` int(5) UNSIGNED NOT NULL,
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `table_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `column_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `comment` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `mimetype` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `transformation` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `transformation_options` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `input_transformation` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `input_transformation_options` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Column information for phpMyAdmin';

-- --------------------------------------------------------

--
-- 테이블 구조 `pma__designer_settings`
--

CREATE TABLE `pma__designer_settings` (
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `settings_data` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Settings related to Designer';

-- --------------------------------------------------------

--
-- 테이블 구조 `pma__export_templates`
--

CREATE TABLE `pma__export_templates` (
  `id` int(5) UNSIGNED NOT NULL,
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `export_type` varchar(10) COLLATE utf8_bin NOT NULL,
  `template_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `template_data` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Saved export templates';

-- --------------------------------------------------------

--
-- 테이블 구조 `pma__favorite`
--

CREATE TABLE `pma__favorite` (
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `tables` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Favorite tables';

-- --------------------------------------------------------

--
-- 테이블 구조 `pma__history`
--

CREATE TABLE `pma__history` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `username` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `db` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `table` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `timevalue` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `sqlquery` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='SQL history for phpMyAdmin';

-- --------------------------------------------------------

--
-- 테이블 구조 `pma__navigationhiding`
--

CREATE TABLE `pma__navigationhiding` (
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `item_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `item_type` varchar(64) COLLATE utf8_bin NOT NULL,
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `table_name` varchar(64) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Hidden items of navigation tree';

-- --------------------------------------------------------

--
-- 테이블 구조 `pma__pdf_pages`
--

CREATE TABLE `pma__pdf_pages` (
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `page_nr` int(10) UNSIGNED NOT NULL,
  `page_descr` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='PDF relation pages for phpMyAdmin';

-- --------------------------------------------------------

--
-- 테이블 구조 `pma__recent`
--

CREATE TABLE `pma__recent` (
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `tables` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Recently accessed tables';

--
-- 테이블의 덤프 데이터 `pma__recent`
--

INSERT INTO `pma__recent` (`username`, `tables`) VALUES
('chanwoong', '[{\"db\":\"petlog\",\"table\":\"walk_post\"},{\"db\":\"petlog\",\"table\":\"petsta_post\"},{\"db\":\"petlog\",\"table\":\"petsta_like\"},{\"db\":\"petlog\",\"table\":\"follow\"},{\"db\":\"petlog\",\"table\":\"petsta_comments\"},{\"db\":\"petlog\",\"table\":\"diary\"},{\"db\":\"petlog\",\"table\":\"user_info\"},{\"db\":\"petlog\",\"table\":\"share_comment\"},{\"db\":\"petlog\",\"table\":\"push_message\"},{\"db\":\"petlog\",\"table\":\"pesta_comments\"}]'),
('dongmin', '[{\"db\":\"petlog\",\"table\":\"user_info\"},{\"db\":\"petlog\",\"table\":\"share_post\"},{\"db\":\"petlog\",\"table\":\"walk_post\"},{\"db\":\"petlog\",\"table\":\"share_comment\"},{\"db\":\"petlog\",\"table\":\"petsta_post\"},{\"db\":\"petlog\",\"table\":\"petsta_like\"},{\"db\":\"petlog\",\"table\":\"mypet_info\"},{\"db\":\"petlog\",\"table\":\"diary\"},{\"db\":\"petlog\",\"table\":\"following\"},{\"db\":\"petlog\",\"table\":\"pesta_comments\"}]'),
('suho', '[{\"db\":\"petlog\",\"table\":\"push_setting\"},{\"db\":\"petlog\",\"table\":\"petsta_post\"},{\"db\":\"petlog\",\"table\":\"petsta_like\"},{\"db\":\"petlog\",\"table\":\"petsta_comments\"},{\"db\":\"petlog\",\"table\":\"notice\"},{\"db\":\"petlog\",\"table\":\"mypet_info\"},{\"db\":\"petlog\",\"table\":\"follow\"},{\"db\":\"petlog\",\"table\":\"diary\"},{\"db\":\"petlog\",\"table\":\"user_info\"},{\"db\":\"petlog\",\"table\":\"share_post\"}]');

-- --------------------------------------------------------

--
-- 테이블 구조 `pma__relation`
--

CREATE TABLE `pma__relation` (
  `master_db` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `master_table` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `master_field` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `foreign_db` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `foreign_table` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `foreign_field` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Relation table';

-- --------------------------------------------------------

--
-- 테이블 구조 `pma__savedsearches`
--

CREATE TABLE `pma__savedsearches` (
  `id` int(5) UNSIGNED NOT NULL,
  `username` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `search_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `search_data` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Saved searches';

-- --------------------------------------------------------

--
-- 테이블 구조 `pma__table_coords`
--

CREATE TABLE `pma__table_coords` (
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `table_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `pdf_page_number` int(11) NOT NULL DEFAULT '0',
  `x` float UNSIGNED NOT NULL DEFAULT '0',
  `y` float UNSIGNED NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Table coordinates for phpMyAdmin PDF output';

-- --------------------------------------------------------

--
-- 테이블 구조 `pma__table_info`
--

CREATE TABLE `pma__table_info` (
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `table_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `display_field` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Table information for phpMyAdmin';

-- --------------------------------------------------------

--
-- 테이블 구조 `pma__table_uiprefs`
--

CREATE TABLE `pma__table_uiprefs` (
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `table_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `prefs` text COLLATE utf8_bin NOT NULL,
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Tables'' UI preferences';

--
-- 테이블의 덤프 데이터 `pma__table_uiprefs`
--

INSERT INTO `pma__table_uiprefs` (`username`, `db_name`, `table_name`, `prefs`, `last_update`) VALUES
('dongmin', 'petlog', 'share_post', '[]', '2020-05-07 14:21:04'),
('dongmin', 'petlog', 'user_info', '{\"sorted_col\":\"`u_pw` ASC\"}', '2020-06-14 10:34:07'),
('dongmin', 'petlog', 'walk_post', '[]', '2020-05-11 09:06:45'),
('suho', 'petlog', 'diary', '[]', '2020-05-31 14:41:11'),
('suho', 'petlog', 'mypet_info', '[]', '2020-05-17 07:45:20'),
('suho', 'petlog', 'notice', '{\"CREATE_TIME\":\"2020-05-25 14:37:09\",\"col_order\":[\"0\",\"1\",\"2\",\"3\"],\"col_visib\":[\"1\",\"1\",\"1\",\"1\"]}', '2020-06-14 05:04:08'),
('suho', 'petlog', 'petsta_post', '[]', '2020-06-07 08:28:44'),
('suho', 'petlog', 'share_post', '{\"sorted_col\":\"`id` ASC\"}', '2020-06-13 17:23:12'),
('suho', 'petlog', 'user_info', '{\"CREATE_TIME\":\"2020-05-05 06:32:58\",\"col_visib\":[\"1\",\"1\",\"1\",\"1\",\"1\"]}', '2020-06-07 17:53:38');

-- --------------------------------------------------------

--
-- 테이블 구조 `pma__tracking`
--

CREATE TABLE `pma__tracking` (
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `table_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `version` int(10) UNSIGNED NOT NULL,
  `date_created` datetime NOT NULL,
  `date_updated` datetime NOT NULL,
  `schema_snapshot` text COLLATE utf8_bin NOT NULL,
  `schema_sql` text COLLATE utf8_bin,
  `data_sql` longtext COLLATE utf8_bin,
  `tracking` set('UPDATE','REPLACE','INSERT','DELETE','TRUNCATE','CREATE DATABASE','ALTER DATABASE','DROP DATABASE','CREATE TABLE','ALTER TABLE','RENAME TABLE','DROP TABLE','CREATE INDEX','DROP INDEX','CREATE VIEW','ALTER VIEW','DROP VIEW') COLLATE utf8_bin DEFAULT NULL,
  `tracking_active` int(1) UNSIGNED NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Database changes tracking for phpMyAdmin';

-- --------------------------------------------------------

--
-- 테이블 구조 `pma__userconfig`
--

CREATE TABLE `pma__userconfig` (
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `timevalue` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `config_data` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='User preferences storage for phpMyAdmin';

--
-- 테이블의 덤프 데이터 `pma__userconfig`
--

INSERT INTO `pma__userconfig` (`username`, `timevalue`, `config_data`) VALUES
('chanwoong', '2020-04-27 10:25:21', '{\"lang\":\"ko\",\"collation_connection\":\"utf8mb4_unicode_ci\"}'),
('dongmin', '2020-04-27 10:25:08', '{\"lang\":\"ko\",\"collation_connection\":\"utf8mb4_unicode_ci\"}'),
('suho', '2020-06-19 15:40:08', '{\"lang\":\"ko\",\"collation_connection\":\"utf8mb4_unicode_ci\",\"ThemeDefault\":\"pmahomme\",\"fontsize\":\"72%\"}');

-- --------------------------------------------------------

--
-- 테이블 구조 `pma__usergroups`
--

CREATE TABLE `pma__usergroups` (
  `usergroup` varchar(64) COLLATE utf8_bin NOT NULL,
  `tab` varchar(64) COLLATE utf8_bin NOT NULL,
  `allowed` enum('Y','N') COLLATE utf8_bin NOT NULL DEFAULT 'N'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='User groups with configured menu items';

-- --------------------------------------------------------

--
-- 테이블 구조 `pma__users`
--

CREATE TABLE `pma__users` (
  `username` varchar(64) COLLATE utf8_bin NOT NULL,
  `usergroup` varchar(64) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Users and their assignments to user groups';

--
-- 덤프된 테이블의 인덱스
--

--
-- 테이블의 인덱스 `pma__bookmark`
--
ALTER TABLE `pma__bookmark`
  ADD PRIMARY KEY (`id`);

--
-- 테이블의 인덱스 `pma__central_columns`
--
ALTER TABLE `pma__central_columns`
  ADD PRIMARY KEY (`db_name`,`col_name`);

--
-- 테이블의 인덱스 `pma__column_info`
--
ALTER TABLE `pma__column_info`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `db_name` (`db_name`,`table_name`,`column_name`);

--
-- 테이블의 인덱스 `pma__designer_settings`
--
ALTER TABLE `pma__designer_settings`
  ADD PRIMARY KEY (`username`);

--
-- 테이블의 인덱스 `pma__export_templates`
--
ALTER TABLE `pma__export_templates`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `u_user_type_template` (`username`,`export_type`,`template_name`);

--
-- 테이블의 인덱스 `pma__favorite`
--
ALTER TABLE `pma__favorite`
  ADD PRIMARY KEY (`username`);

--
-- 테이블의 인덱스 `pma__history`
--
ALTER TABLE `pma__history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `username` (`username`,`db`,`table`,`timevalue`);

--
-- 테이블의 인덱스 `pma__navigationhiding`
--
ALTER TABLE `pma__navigationhiding`
  ADD PRIMARY KEY (`username`,`item_name`,`item_type`,`db_name`,`table_name`);

--
-- 테이블의 인덱스 `pma__pdf_pages`
--
ALTER TABLE `pma__pdf_pages`
  ADD PRIMARY KEY (`page_nr`),
  ADD KEY `db_name` (`db_name`);

--
-- 테이블의 인덱스 `pma__recent`
--
ALTER TABLE `pma__recent`
  ADD PRIMARY KEY (`username`);

--
-- 테이블의 인덱스 `pma__relation`
--
ALTER TABLE `pma__relation`
  ADD PRIMARY KEY (`master_db`,`master_table`,`master_field`),
  ADD KEY `foreign_field` (`foreign_db`,`foreign_table`);

--
-- 테이블의 인덱스 `pma__savedsearches`
--
ALTER TABLE `pma__savedsearches`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `u_savedsearches_username_dbname` (`username`,`db_name`,`search_name`);

--
-- 테이블의 인덱스 `pma__table_coords`
--
ALTER TABLE `pma__table_coords`
  ADD PRIMARY KEY (`db_name`,`table_name`,`pdf_page_number`);

--
-- 테이블의 인덱스 `pma__table_info`
--
ALTER TABLE `pma__table_info`
  ADD PRIMARY KEY (`db_name`,`table_name`);

--
-- 테이블의 인덱스 `pma__table_uiprefs`
--
ALTER TABLE `pma__table_uiprefs`
  ADD PRIMARY KEY (`username`,`db_name`,`table_name`);

--
-- 테이블의 인덱스 `pma__tracking`
--
ALTER TABLE `pma__tracking`
  ADD PRIMARY KEY (`db_name`,`table_name`,`version`);

--
-- 테이블의 인덱스 `pma__userconfig`
--
ALTER TABLE `pma__userconfig`
  ADD PRIMARY KEY (`username`);

--
-- 테이블의 인덱스 `pma__usergroups`
--
ALTER TABLE `pma__usergroups`
  ADD PRIMARY KEY (`usergroup`,`tab`,`allowed`);

--
-- 테이블의 인덱스 `pma__users`
--
ALTER TABLE `pma__users`
  ADD PRIMARY KEY (`username`,`usergroup`);

--
-- 덤프된 테이블의 AUTO_INCREMENT
--

--
-- 테이블의 AUTO_INCREMENT `pma__bookmark`
--
ALTER TABLE `pma__bookmark`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- 테이블의 AUTO_INCREMENT `pma__column_info`
--
ALTER TABLE `pma__column_info`
  MODIFY `id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- 테이블의 AUTO_INCREMENT `pma__export_templates`
--
ALTER TABLE `pma__export_templates`
  MODIFY `id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- 테이블의 AUTO_INCREMENT `pma__history`
--
ALTER TABLE `pma__history`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- 테이블의 AUTO_INCREMENT `pma__pdf_pages`
--
ALTER TABLE `pma__pdf_pages`
  MODIFY `page_nr` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- 테이블의 AUTO_INCREMENT `pma__savedsearches`
--
ALTER TABLE `pma__savedsearches`
  MODIFY `id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
