

// user details   
//first name last name, date of birth, email, phone number


import React, {useEffect} from 'react';
import { useState } from 'react';
import {asyncApiRequest} from "@/tools/request";

export default function AccountSettings ({ userDetails }) {
    const [isEditing, setIsEditing] = useState(false);
    const [editableDetails, setEditableDetails] = useState({});

    useEffect(() => {
        if (userDetails) {
            setEditableDetails(userDetails);
        }
    }, [userDetails]);

    const handleEditClick = () => {
        setIsEditing(!isEditing);
    }

    const handleChange = (e) => {
        const { name, value } = e.target;
        setEditableDetails((prevDetails) => ({
                ...prevDetails,
                [name]: value,
        }));
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await asyncApiRequest("PUT", `/api/users/${email}`, editableDetails);
            if (response.ok) {
                setIsEditing(false);
            } else {
                console.error("Error updating user details: ", response);
            }
        } catch (error) {
            console.error("Error updating user details: ", error);
        }
    };

    if (!userDetails) {
        return <p>Loading...</p>;
    }


    return (
        <div>
            <h2>User Profile Settings</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    First Name:
                    <input
                        type="text"
                        name="firstName"
                        value={editableDetails.firstName}
                        onChange={handleChange}
                        readOnly={!isEditing}
                    />
                </label>
                <label>
                    Last Name:
                    <input
                        type="text"
                        name="lastName"
                        value={editableDetails.lastName}
                        onChange={handleChange}
                        readOnly={!isEditing}
                    />
                </label>
                <label>
                    Date of Birth:
                    <input
                        type="date"
                        name="dateOfBirth"
                        value={editableDetails.date}
                        onChange={handleChange}
                        readOnly={!isEditing}
                    />
                </label>
                <label>
                    Email:
                    <input
                        type="email"
                        name="email"
                        value={editableDetails.email}
                        readOnly
                    />
                </label>
                <label>
                    Phone Number:
                    <input
                        type="tel"
                        name="phoneNumber"
                        value={editableDetails.phoneNumber}
                        onChange={handleChange}
                        readOnly={!isEditing}
                    />
                </label>
                {isEditing && <input type="submit" value="Submit" />}
            </form>
            <button onClick={handleEditClick}>
                {isEditing ? 'Cancel' : 'Edit Profile'}
            </button>
        </div>
    );
}