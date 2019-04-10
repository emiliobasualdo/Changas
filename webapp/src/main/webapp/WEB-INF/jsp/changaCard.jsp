<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons" />
</head>

<style>
    @import url(https://fonts.googleapis.com/css?family=Josefin+Sans:400,300,300italic,400italic,600,700,600italic,700italic);
    body {
        font-family: "Josefin Sans", sans-serif;
        line-height: 1;
        padding: 20px;
        height: 100%;
        background: #eee;
    }
    .changaCard-option {
        /*width: 32%;*/
        background: white;
        float: left;
        padding: 2%;
        text-align: center;
        transition: all .3s ease-in-out;
    }
    /*.changaCard-option:nth-child(even) {*/
    /*margin: 0 2%;*/
    /*}*/
    .changaCard-option:hover {
        cursor: pointer;
        box-shadow: 0px 2px 30px rgba(0, 0, 0, 0.3);
        -webkit-transform: scale(1.04);
        transform: scale(1.04);
    }
    .changaCard-option:hover i,  .changaCard-option:hover h1,  .changaCard-option:hover span,  .changaCard-option:hover b {
        color: #26B3BA;
    }
    .changaCard-option:hover .front {
        opacity: 0;
        visibility: hidden;
    }
    .changaCard-option:hover .back {
        opacity: 1 !important;
        visibility: visible !important;
    }
    .changaCard-option:hover .back a.button {
        -webkit-transform: translateY(0px) !important;
        transform: translateY(0px) !important;
    }
    .changaCard-option hr {
        border: none;
        border-bottom: 1px solid #F0F0F0;
    }
    .changaCard-option i {
        font-size: 3rem;
        color: #D8D8D8;
        transition: all .3s ease-in-out;
    }
    .changaCard-option h1 {
        margin: 10px 0;
        color: #212121;
        transition: all .3s ease-in-out;
    }
    .changaCard-option p {
        color: #999;
        padding: 0 10px;
        line-height: 1.3;
    }
    .changaCard-option .price {
        position: relative;
    }
    .changaCard-option .price .front span.price {
        font-size: 2rem;
        text-transform: uppercase;
        margin-top: 20px;
        display: block;
        font-weight: 700;
        position: relative;
    }
    .changaCard-option .price .front span.price b {
        position: absolute;
        font-size: 1rem;
        margin-left: 2px;
        font-weight: 600;
    }
    .changaCard-option .price .back {
        opacity: 0;
        visibility: hidden;
        transition: all .3s ease-in-out;
    }
    .changaCard-option .price .back a.button {
        background: #26B3BA;
        padding: 15px 20px;
        display: inline-block;
        text-decoration: none;
        color: white;
        position: absolute;
        font-size: 13px;
        top: -5px;
        left: 0;
        right: 0;
        width: 150px;
        margin: auto;
        text-transform: uppercase;
        -webkit-transform: translateY(20px);
        transform: translateY(20px);
        transition: all .3s ease-in-out;
    }
    .changaCard-option .price .back a.button:hover {
        background: #f62d3d;
    }

    @media screen and (max-width: 600px) {
        .changaCard-option {
            padding: 5%;
            width: 90%;
        }
        .changaCard-option:nth-child(even) {
            margin: 30px 0 !important;
        }
    }
</style>

    <div class="changaCard-option">
        <h1>
            <c:out value="${requestScope.title}" />
        </h1>
        <span class="">
            <i class="fas fa-map-marker-alt fas-2x"></i>
            <small>
                <c:out value="${requestScope.neighborhood}" />, <c:out value="${requestScope.street}" /> <c:out value="${requestScope.number}" />
            </small><br>
            </span>
        <hr />
        <p>
            <c:out value="${requestScope.description}" />
        </p>
        <hr />
        <div class="price">
            <div class="front">
                <span class="price">
                    <c:out value="${requestScope.price}" />$
                </span>
            </div>
            <div class="back">
                <a href="changa?id=<c:out value="${requestScope.changa_id}"/>" class="button">
                    <spring:message code="changaCard.button" />
                </a>
            </div>
        </div>
    </div>

</html>
