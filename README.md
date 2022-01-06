# Employee_managment_demo
notes

1- you can run that project using main spring boot class EmployeeManagmentApplication

2- also Dockerfile and docker-compose is ready, so you can run it also that way
sudo docker-compose build
sudo docker-compose up

in case you will run it normally you can change properties in application.yml
and if you will run it with Docker you can change it in .env file in the main project directory

3- for status i used Enum to handle it also i implemented stateMachine

4- after run the app you can open http://localhost:8080/swagger-ui/index.html to see endpoints docs