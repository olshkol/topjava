<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal form</title>
</head>
<body>
    <form method="post" action="meals" name="addMeal">
        <input type="text" readonly name="id" value="${meal.id}"/>
        <br>
        Date:<br>
        <input type="datetime-local" name="date" value="${meal.dateTime}">
        <br>
        Description:<br>
        <input type="text" name="description" value="${meal.description}">
        <br>
        Calories:<br>
        <input type="text" name="calories" value="${meal.calories}">
        <br>
        <br>
        <input type="submit" value="Add meal">
    </form>
</body>
</html>
