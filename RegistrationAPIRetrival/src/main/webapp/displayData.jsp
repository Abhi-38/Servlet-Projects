<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="com.abhi.prj.Member" %>
<!DOCTYPE html>
<html>
<head>
    <title>Display Data</title>
    <style>
        table {
            border-collapse: collapse;
            width: 50%;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h2>Retrieved Data</h2>

    <table>
        <thead>
            <tr>
                <th>Member Name</th>
                <th>Member Password</th>
                <th>Member Email</th>
                <th>Member Phone</th>
                <!-- Add more columns as needed -->
            </tr>
        </thead>
        <tbody>
            <% 
                List<Member> members = (List<Member>) request.getAttribute("members");
                for (Member member : members) {
            %>
            <tr>
                <td><%= member.getMemberName() %></td>
                <td><%= member.getMemberPwd() %></td>
                <td><%= member.getMemberEmail() %></td>
                <td><%= member.getMemberPhone() %></td>
            </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>