<?php

    class DbConnection {

        private $con;

        function __construct(){}

        function connect(){
            require_once(dirname(__FILE__) . '/constants.php');

            $this->con = mysqli_connect(SERVER, USERNAME, PASSWORD, DB_NAME, DB_PORT);
        
            if (!$this->con) {
                die("Connection to database failed: " . mysqli_connect_error());
            }
            #echo "Database connected successfully";

            return $this->con;
        }
    }

?>