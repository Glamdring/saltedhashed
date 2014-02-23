<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="includes.jsp" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
        <div class="row">
            <div class="col-lg-12">
            <p>
                Below is the flow needed for verifying your site's password storage mechanism
                <ol>
                    <li>Register on this site.</li>
                    <li>Add your site to the list.</li>
                    <li>Specify "Endpoint path" to be the path to the service which you are going to expose in the next step.</li>
                    <li>Expose a RESTful service endpoint that conforms to the requirements below.</li>
                    <li>Place a badge we provide on your registration form to show your users that you store passwords securely.</li>
                </ol>
            </p>
            <p>
                The RESTful service that we need in order to verify your password storage mechanism must use the same functionality that is used for storing user passwords in the database.
                <br />
                Input:
                <code><pre>{
    password: "randompassword"
}</pre></code>

                Output:
                <code><pre>{
    algorithm: "BCRYPT", // the one-way password transformation algorithm you use, one of: BCRYPT, PBKDF2, SCRYPT
    hash: "$2a$10$rBV2JDeWW3.vKyeQcM8fFO4777l4bVeQgDL6VIkxqlzQ7TCa", // the hash that you store in the database. If it's binary, use BASE64 to encode it
    salt: "Jfdsaj8ga", // (optional) the salt used, in case it's not included in the hash (e.g. for PBKDF2). If it's binary, use BASE64 to encode it
    algorithmDetails: { // (optional) contains additional information about the algorithm (applicable only for PBKDF2)
        hashFunction: "SHA-256", // the hash function used with the algorithm. One of SHA1, SHA-256, SHA-512
        iterations: 10, // the number of iterations used
        keySize: 48 // the key size
    }
}</pre></code>
            </p>
            </div>
        </div>
</t:template>