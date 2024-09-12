<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
        }

        table {
            width: 100%;
        }

        tr, td {
            padding: 10px;
        }

        input {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background-color: #4caf50;
            color: #fff;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
<title>Register here</title>
</head>
<body>
	<form action="Register" method="post">
		<table>
			<tr>
				<td>User name: </td>
				<td><input type="text" name="uname"></td>			
			</tr>
			
			<tr>
				<td>Password: </td>
				<td><input type="password" name="password"></td>			
			</tr>
			
			<tr>
				<td>Email: </td>
				<td><input type="email" name="email"></td>			
			</tr>
			
			<tr>
				<td>Phone no.: </td>
				<td><input type="tel" name="phone"></td>			
			</tr>
			
			<tr>
				<td colspan="2"><input type="submit" value="register"></td>			
			</tr>
			
		</table>
	</form>
</body>
</html>