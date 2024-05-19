

// user details   
//first name last name, date of birth, email, phone number


import React from 'react';
import { useState } from 'react';

export default function AccountSettings ({ userDetails }) {
    const [isEditing, setIsEditing] = useState(false);

    const handleEditClick = () => {
        setIsEditing(!isEditing);
    }
    return (
        <div>
            <h2>User Profile Settings</h2>
            {isEditing ? (
                <form>
                    <label>
                        First Name:
                        <input type="text" name="firstName"/>
                    </label>
                    <label>
                        Last Name:
                        <input type="text" name="lastName"/>
                    </label>
                    <label>
                        Date of Birth:
                        <input type="date" name="dob"/>
                    </label>
                    <label>
                        Email:
                        <input type="email" name="email"/>
                    </label>
                    <label>
                        Phone Number:
                        <input type="tel" name="phoneNumber"/>
                    </label>
                    <input type="submit" value="Submit"/>
                </form>

            ) : (
                userDetails && (
                    <div>
                        <p>First Name: {userDetails.firstName}</p>
                        <p>Last Name: {userDetails.lastName}</p>
                        <p>Date of Birth: {userDetails.dob}</p>
                        <p>Email: {userDetails.email}</p>
                        <p>Phone Number: {userDetails.phoneNumber}</p>
                    </div>
                )
            )}
            <button onClick={handleEditClick}>
                {isEditing ? 'Save Changes' : 'Edit Profile'}
            </button>
        </div>
    );
}