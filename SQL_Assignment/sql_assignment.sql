/* I. CREATE TABLES */

-- faculty (Khoa trong trường)
create table faculty (
	id number primary key,
	name nvarchar2(30) not null
);

-- subject (Môn học)
create table subject(
	id number primary key,
	name nvarchar2(100) not null,
	lesson_quantity number(2,0) not null -- tổng số tiết học
);

-- student (Sinh viên)
create table student (
	id number primary key,
	name nvarchar2(30) not null,
	gender nvarchar2(10) not null, -- giới tính
	birthday date not null,
	hometown nvarchar2(100) not null, -- quê quán
	scholarship number, -- học bổng
	faculty_id number not null constraint faculty_id references faculty(id) -- mã khoa
);

-- exam management (Bảng điểm)
create table exam_management(
	id number primary key,
	student_id number not null constraint student_id references student(id),
	subject_id number not null constraint subject_id references subject(id),
	number_of_exam_taking number not null, -- số lần thi (thi trên 1 lần được gọi là thi lại)
	mark number(4,2) not null -- điểm
);



/*================================================*/

/* II. INSERT SAMPLE DATA */

-- subject
insert into subject (id, name, lesson_quantity) values (1, n'Cơ sở dữ liệu', 45);
insert into subject values (2, n'Trí tuệ nhân tạo', 45);
insert into subject values (3, n'Truyền tin', 45);
insert into subject values (4, n'Đồ họa', 60);
insert into subject values (5, n'Văn phạm', 45);


-- faculty
insert into faculty values (1, n'Anh - Văn');
insert into faculty values (2, n'Tin học');
insert into faculty values (3, n'Triết học');
insert into faculty values (4, n'Vật lý');


-- student
insert into student values (1, n'Nguyễn Thị Hải', n'Nữ', to_date('19900223', 'YYYYMMDD'), 'Hà Nội', 130000, 2);
insert into student values (2, n'Trần Văn Chính', n'Nam', to_date('19921224', 'YYYYMMDD'), 'Bình Định', 150000, 4);
insert into student values (3, n'Lê Thu Yến', n'Nữ', to_date('19900221', 'YYYYMMDD'), 'TP HCM', 150000, 2);
insert into student values (4, n'Lê Hải Yến', n'Nữ', to_date('19900221', 'YYYYMMDD'), 'TP HCM', 170000, 2);
insert into student values (5, n'Trần Anh Tuấn', n'Nam', to_date('19901220', 'YYYYMMDD'), 'Hà Nội', 180000, 1);
insert into student values (6, n'Trần Thanh Mai', n'Nữ', to_date('19910812', 'YYYYMMDD'), 'Hải Phòng', null, 3);
insert into student values (7, n'Trần Thị Thu Thủy', n'Nữ', to_date('19910102', 'YYYYMMDD'), 'Hải Phòng', 10000, 1);


-- exam_management
insert into exam_management values (1, 1, 1, 1, 3);
insert into exam_management values (2, 1, 1, 2, 6);
insert into exam_management values (3, 1, 2, 2, 6);
insert into exam_management values (4, 1, 3, 1, 5);
insert into exam_management values (5, 2, 1, 1, 4.5);
insert into exam_management values (6, 2, 1, 2, 7);
insert into exam_management values (7, 2, 3, 1, 10);
insert into exam_management values (8, 2, 5, 1, 9);
insert into exam_management values (9, 3, 1, 1, 2);
insert into exam_management values (10, 3, 1, 2, 5);
insert into exam_management values (11, 3, 3, 1, 2.5);
insert into exam_management values (12, 3, 3, 2, 4);
insert into exam_management values (13, 4, 5, 2, 10);
insert into exam_management values (14, 5, 1, 1, 7);
insert into exam_management values (15, 5, 3, 1, 2.5);
insert into exam_management values (16, 5, 3, 2, 5);
insert into exam_management values (17, 6, 2, 1, 6);
insert into exam_management values (18, 6, 4, 1, 10);



/*================================================*/

/* III. QUERY */


 /********* A. BASIC QUERY *********/

-- 1. Liệt kê danh sách sinh viên sắp xếp theo thứ tự:
--      a. id tăng dần
--      b. giới tính
--      c. ngày sinh TĂNG DẦN và học bổng GIẢM DẦN
    select * from student order by student.id;
    select * from student order by student.gender;
    select * from student order by student.birthday ASC, student.scholarship DESC;

-- 2. Môn học có tên bắt đầu bằng chữ 'T'
    select subject.name
    from subject
    where subject.name like 'T%';

-- 3. Sinh viên có chữ cái cuối cùng trong tên là 'i'
    select student.name
    from student
    where student.name like '%i';

-- 4. Những khoa có ký tự thứ hai của tên khoa có chứa chữ 'n'
    select faculty.name
    from faculty
    where faculty.name like '_n%';

-- 5. Sinh viên trong tên có từ 'Thị'
    select student.name
    from student
    where student.name like '%Thị%';

-- 6. Sinh viên có ký tự đầu tiên của tên nằm trong khoảng từ 'a' đến 'm', sắp xếp theo họ tên sinh viên
    select student.name
    from student
    where student.name between 'A' and 'M'
    group by student.name;

-- 7. Sinh viên có học bổng lớn hơn 100000, sắp xếp theo mã khoa giảm dần
    select student.scholarship
    from student
    where student.scholarship > 100000
    order by faculty_id desc;

-- 8. Sinh viên có học bổng từ 150000 trở lên và sinh ở Hà Nội
    select student.scholarship, student.hometown
    from student
    where student.scholarship > 150000 and hometown = 'Hà Nội';

-- 9. Những sinh viên có ngày sinh từ ngày 01/01/1991 đến ngày 05/06/1992
    select *
    from student
    where student.birthday between to_date('01/01/1991', 'DD/MM/YYYY') and to_date('05/06/1992', 'DD/MM/YYYY');

-- 10. Những sinh viên có học bổng từ 80000 đến 150000
    select *
    from student
    where student.scholarship between 80000 and 150000;

-- 11. Những môn học có số tiết lớn hơn 30 và nhỏ hơn 45
    select *
    from subject
    where subject.lesson_quantity > 30 and subject.lesson_quantity < 45;


-------------------------------------------------------------------

/********* B. CALCULATION QUERY *********/

-- 1. Cho biết thông tin về mức học bổng của các sinh viên, gồm: Mã sinh viên, Giới tính, Mã
		-- khoa, Mức học bổng. Trong đó, mức học bổng sẽ hiển thị là “Học bổng cao” nếu giá trị
		-- của học bổng lớn hơn 500,000 và ngược lại hiển thị là “Mức trung bình”.
    select student.id, gender, student.faculty_id,
    case when scholarship > 500000 then 'Học bổng cao' else 'Học bổng trung bình' end scholarship
    from student;

-- 2. Tính tổng số sinh viên của toàn trường
    select count(id) as student_total
    from student;

-- 3. Tính tổng số sinh viên nam và tổng số sinh viên nữ.
    select gender, count(id)
    from student
    group by student.gender;

-- 4. Tính tổng số sinh viên từng khoa
    select faculty.name, count(student.id)
    from student,faculty
    where student.faculty_id = faculty.id
    group by faculty.name;

-- 5. Tính tổng số sinh viên của từng môn học
    select subject.name, count(exam_management.student_id)
    from exam_management, subject
    where subject.id = exam_management.subject_id
    group by subject.name;

-- 6. Tính số lượng môn học mà sinh viên đã học
    select student_id, count(subject_id)
    from exam_management
    group by student_id;

-- 7. Tổng số học bổng của mỗi khoa
    select faculty.name, count(student.scholarship)
    from faculty, student
    where faculty.id = student.faculty_id
    group by faculty.name;

-- 8. Cho biết học bổng cao nhất của mỗi khoa
    select faculty.name, max(student.scholarship)
    from faculty, student
    where faculty.id = student.faculty_id
    group by faculty.name;

-- 9. Cho biết tổng số sinh viên nam và tổng số sinh viên nữ của mỗi khoa
    select faculty.name, gender, count(student.id) total
    from student, faculty
    where gender = 'Nam' and faculty.id = student.faculty_id
    group by faculty.name, gender
    union
    select faculty.name, gender, count(student.id) total
    from student, faculty
    where gender = 'Nữ' and faculty.id = student.faculty_id
    group by faculty.name, gender;

-- 10. Cho biết số lượng sinh viên theo từng độ tuổi
    select student.birthday, count(student.id)
    from student
    group by student.birthday;

-- 11. Cho biết những nơi nào có ít nhất 2 sinh viên đang theo học tại trường
    select student.hometown, count(student.id) as total
    from student
    group by hometown
    having count(student.id) > 2;

-- 12. Cho biết những sinh viên thi lại ít nhất 2 lần
    select student.name, exam_management.subject_id, count(exam_management.number_of_exam_taking)
    from exam_management, student
    where student.id = exam_management.student_id
    group by student.name, exam_management.subject_id
    having count(number_of_exam_taking) >= 2;

-- 13. Cho biết những sinh viên nam có điểm trung bình lần 1 trên 7.0
    select student.name, avg(exam_management.mark)
    from student, exam_management
    where student.id = exam_management.student_id and student.gender = 'Nam' and exam_management.number_of_exam_taking = 1
    group by student.name
    having avg(exam_management.mark) > 7;

-- 14. Cho biết danh sách các sinh viên rớt ít nhất 2 môn ở lần thi 1 (rớt môn là điểm thi của môn không quá 4 điểm)
    select student.name
    from student, exam_management
    where exam_management.number_of_exam_taking = 1 and exam_management.mark <= 4 and student.id = exam_management.student_id
    group by student.name;

-- 15. Cho biết danh sách những khoa có nhiều hơn 2 sinh viên nam
    select faculty.name, count(student.gender)
    from faculty, student
    where student.faculty_id = faculty.id
    and student.gender = 'Nam'
    group by faculty.name
    having count(student.gender) > 2;

-- 16. Cho biết những khoa có 2 sinh viên đạt học bổng từ 200000 đến 300000
    select faculty.name, count(student.id)
    from student, faculty
    where student.scholarship >= 200000 and student.scholarship <= 300000
    group by faculty.name
    having count(student.id) = 2;

-- 17. Cho biết sinh viên nào có học bổng cao nhất
    select student.name, max(student.scholarship)
    from student
    where student.scholarship = (select max(student.scholarship) from student)
    group by student.name;


-------------------------------------------------------------------

/********* C. DATE/TIME QUERY *********/

-- 1. Sinh viên có nơi sinh ở Hà Nội và sinh vào tháng 02
    select student.name
    from student
    where to_char(birthday, 'MM') = '02' and student.hometown = 'Hà Nội';

-- 2. Sinh viên có tuổi lớn hơn 20
    select student.name, current_year - to_number(to_char(student.birthday, 'YYYY')) age
    from student, (select to_number(to_char(sysdate, 'YYYY')) current_year from dual)
    where current_year - to_number(to_char(student.birthday, 'YYYY')) > 20;

-- 3. Sinh viên sinh vào mùa xuân năm 1990
    select student.name
    from student
    where to_char(student.birthday, 'MM') in ('01', '02', '03') and to_char(student.birthday, 'YYYY') = '1990';


-------------------------------------------------------------------


/********* D. JOIN QUERY *********/

-- 1. Danh sách các sinh viên của khoa ANH VĂN và khoa VẬT LÝ
    select student.name
    from student
    join faculty on student.faculty_id = faculty.id
    where faculty.name = 'Anh - Văn' or faculty.name = 'Vật lý';

-- 2. Những sinh viên nam của khoa ANH VĂN và khoa TIN HỌC
    select student.name, faculty.name
    from student,faculty
    where student.faculty_id = faculty.id and (faculty.name = 'Anh - Văn' or faculty.name = 'Tin học') and student.gender = 'Nam';

-- 3. Cho biết sinh viên nào có điểm thi lần 1 môn cơ sở dữ liệu cao nhất
    select student.name, exam_management.mark
    from exam_management
    join student on student.id = exam_management.student_id
    where number_of_exam_taking = 1 and subject_id = 1
    and mark = (select max(mark) from exam_management where number_of_exam_taking = 1 and subject_id = 1);

-- 4. Cho biết sinh viên khoa anh văn có tuổi lớn nhất.
    select student.name, faculty.name, current_year - to_number(to_char(student.birthday, 'YYYY')) age
    from faculty , student, (select to_number(to_char(sysdate, 'YYYY')) current_year from dual)
    where faculty.name = 'Anh - Văn' and student.faculty_id = faculty.id
    and current_year - to_number(to_char(student.birthday, 'YYYY')) = (select max(current_year - to_number(to_char(student.birthday, 'YYYY'))) from student);

-- 5. Cho biết khoa nào có đông sinh viên nhất
    select faculty.name, count(student.id)
    from faculty, student
    where faculty.id = student.faculty_id
    group by faculty.name
    having count(student.faculty_id) >= all(select count(student.id) from student group by student.faculty_id);

-- 6. Cho biết khoa nào có đông nữ nhất
    select faculty.name, gender, count(gender)
    from faculty,student
    where faculty.id = student.faculty_id and gender = 'Nữ'
    group by faculty.name, gender having count(student.faculty_id) >= all(select count(gender) from student where gender = 'Nữ' group by student.faculty_id);

-- 7. Cho biết những sinh viên đạt điểm cao nhất trong từng môn
    select student.name, max(mark)
    from exam_management, student
    where  exam_management.student_id = student.id
    group by student.name;

-- 8. Cho biết những khoa không có sinh viên học
    select faculty.name, count(student.id)
    from faculty
    join student on faculty.id = student.faculty_id
    group by faculty.name having count(student.id) = 0;

-- 9. Cho biết sinh viên chưa thi môn cơ sở dữ liệu
    select student.name, count(subject_id)
    from exam_management
    join student on exam_management.student_id = student.id
    where not exam_management.subject_id = 1
    group by student.name;

-- 10. Cho biết sinh viên nào không thi lần 1 mà có dự thi lần 2
    select student.name, number_of_exam_taking
    from exam_management
    join student on student.id = exam_management.student_id
    where number_of_exam_taking = 2 and not exists (select id, student_id, subject_id, exam_management.number_of_exam_taking, mark
    from exam_management where number_of_exam_taking = 1 and student.id = exam_management.student_id);
