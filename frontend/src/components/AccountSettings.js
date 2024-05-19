

// user details   
//first name last name, date of birth, email, phone number


import React from 'react';

export default function AccountSettings() {
    return (
        <div>
            <h2>User Profile Settings</h2>
            <form>
                <label>
                    First Name:
                    <input type="text" name="firstName" />
                </label>
                <label>
                    Last Name:
                    <input type="text" name="lastName" />
                </label>
                <label>
                    Date of Birth:
                    <input type="date" name="dob" />
                </label>
                <label>
                    Email:
                    <input type="email" name="email" />
                </label>
                <label>
                    Phone Number:
                    <input type="tel" name="phoneNumber" />
                </label>
                <input type="submit" value="Submit" />
            </form>
        </div>
    );
}