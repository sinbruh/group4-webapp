import React from 'react';

export default function AdministratorSettings() {
    return (
        <div>
            <h2>Admin Profile Settings</h2>
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