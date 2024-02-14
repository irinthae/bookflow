@echo off
chcp 65001

SET STUDENT_NAME="GRAMSCH_IRINA"
SET CURRENT_DATE=%DATE:~-4%-%DATE:~-7,2%-%DATE:~-10,2%
SET TARGET=..
for %%I in (.) do set PROJECT_NAME=%%~nxI

:generate
java -jar lib/zipper.jar -output %TARGET%/%STUDENT_NAME%-%PROJECT_NAME%.zip -zipDir %PROJECT_NAME% -open true