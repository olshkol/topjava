<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="meals" scope="request" type="java.util.List"/>
<html>
<head>
    <title>Meals</title>

    <style>
        #meals {
            font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
            color: white;
            border-collapse: collapse;
            width: 100%;
        }

        #meals td, #meals th {
            border: 1px solid #ddd;
            padding: 8px;
        }

        #meals thead{
            background-color: #f2f2f2;
            font-weight:bold;
            color: black;
        }

        #meals th {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: left;
            background-color: #4CAF50;
            color: white;
        }
    </style>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <h2>Meals</h2>
    <p><a href="mealForm.jsp">Add meal</a></p>
    <table id="meals">
        <thead>
            <tr>
                <td>Date</td>
                <td>Description</td>
                <td>Calories</td>
                <td colspan="2">Actions</td>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${meals}" var="meal">
            <tr bgcolor="${meal.excess ? '#dc143c' : '#006400'}">
                <fmt:parseDate value="${ meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${ parsedDateTime }" /></td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
                <td><a href="meals?action=delete&mealId=${meal.id}">Delete</a></td>
                <td><a href="meals?action=update&mealId=${meal.id}">Update</a></td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
