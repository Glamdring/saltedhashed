    <div class="row">
            <div class="col-lg-12">

        <p>

        <h2>Why do you need to use secure algorithms when storing
            passwords?</h2>
            Standard hashing functions are not designed for storing passwords. If
            an attacker gets your database, modern hardware allows for easy
            brute-force attacks. And when you leak the password of your users and
            they use the same password on other sites, all their accounts are
            compromised.
            <a
                href="http://security.stackexchange.com/questions/4781/do-any-security-experts-recommend-bcrypt-for-password-storage/6415#6415">See
                here for more details</a>. And <a
                href="http://security.blogoverflow.com/2011/11/why-passwords-should-be-hashed/">read</a>
            this post explaining why you need to hash passwords in the first
            place.
        </p>

        <p>
                <h2>How?</h2>
                Below is are the steps needed for verifying your site's password storage mechanism:
                <ol>
                    <li>Register on this site.</li>
                    <li>Add your site to the list.</li>
                    <li>Specify "Endpoint path" to be the path to the service which you are going to expose in the next step.</li>
                    <li>Expose a RESTful service endpoint that conforms to the requirements below. We invoke that endpoint periodically (roughly once a day) to test your compliance.</li>
                    <li>Place a badge we provide on your registration form to show your users that you store passwords securely.</li>
                </ol>
            </p>
            <p>
                The RESTful service that we need in order to verify your password storage mechanism must use the same functionality that is used for storing user passwords in the database.
                <br /><br />
                It needs to accept the following input:
                <pre><code>Content-Type: application/json

{
    password: "randompassword"
}</code></pre>

                And produce the following output as a response:
                <pre><code>Content-Type: application/json

{
    algorithm: "BCRYPT", // the one-way password transformation algorithm you use, one of: BCRYPT, PBKDF2, SCRYPT.
    hash: "$2a$10$rBV2JDeWW3.vKyeQcM8fFO47l4bVeQgDL6VIkxqlzQ7TCa", // the hash that you store in the database. If it's binary, BASE64-encode it.
    salt: "vKyeQcM8fFO47l4bVe", // (optional, used only for PBKDF2) the salt used if it's not included in the hash. If it's binary, BASE64-encode it.
    algorithmDetails: { // (optional, used only for PBKDF2) contains additional information about the algorithm.
        hashFunction: "SHA-256", // the hash function used with the algorithm. One of SHA1, SHA-224, SHA-256, SHA-384, SHA-512.
        iterations: 100, // the number of iterations used.
        keySize: 48 // the key size.
    }
}</code></pre>
            </p>

            <p>
                <h2>What do you get for being compliant?</h2>
                Your site stands out and is recognized as one that stores passwords securely. Even though users are not experts, a simple page linked to the badge we provide explains why storing passwords securely is important for them.
            </p>

            <p>
                <h2>Are compliant sites really secure?</h2>
                    Does having an endpoint implementing the right algorithm mean a
                    website is using this algorithm for storing passwords? Not
                    necessarily, but if they have the right algorithm implemented in their
                    system, why not use it for the passwords? The only scenario is that
                    there are old, unmigrated passwords. A simple way to migrate passwords
                    to a new algorithm is either to send a "change your password" email,
                    or to simply re-hash the password on the next login of the user (when
                    you have the plantext password submitted with the request)
        </p>
    </div>
</div>