

// user details   
//first name last name, date of birth, email, phone number


import React, {useEffect} from 'react';
import { useState } from 'react';
import {asyncApiRequest} from "@/tools/request";

export default function AccountSettings ({ userDetails }) {
    const [isEditing, setIsEditing] = useState(false);
    const [editableDetails, setEditableDetails] = useState({});

    useEffect(() => {
        setEditableDetails(userDetails)
    }, [userDetails]);

    const handleEditClick = () => {
        if (isEditing) {
            setEditableDetails(userDetails);
        }
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
        <div className={"max-w-x1 mx-auto p-6 bg-white shadow-md rounded-md"}>
            <h2 className={"text-2x1 font-semibold mb-4"}>User Profile Settings</h2>
            <form className={"space-y-2"}>
                <label className={"block text-gray-700"}>
                    First Name:
                    <input
                        type="text"
                        name="firstName"
                        value={editableDetails.firstName}
                        onChange={handleChange}
                        readOnly={!isEditing}
                        className={`w-full px-4 py-2 border ${isEditing ? 'border-blue-500': 'border-gray-300'} rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent`}
                    />
                </label>
                <label className={"block text-gray-700"}>
                    Last Name:
                    <input
                        type="text"
                        name="lastName"
                        value={editableDetails.lastName}
                        onChange={handleChange}
                        readOnly={!isEditing}
                        className={"w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"}
                    />
                </label>
                <label className={"block text-gray-700"}>
                    Date of Birth:
                    <input
                        type="date"
                        name="dateOfBirth"
                        value={editableDetails.date}
                        onChange={handleChange}
                        readOnly={!isEditing}
                        className={"w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"}
                    />
                </label>
                <label className={"block text-gray-700"}>
                    Email:
                    <input
                        type="email"
                        name="email"
                        value={editableDetails.email}
                        readOnly
                        onChange={handleChange}
                        readOnly={!isEditing}
                        className={"w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"}
                    />
                </label>
                <label className={"block text-gray-700"}>
                    Phone Number:
                    <input
                        type="tel"
                        name="phoneNumber"
                        value={editableDetails.phoneNumber}
                        onChange={handleChange}
                        readOnly={!isEditing}
                        className={"w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"}
                    />
                </label>
                {isEditing && (
                    <div className="flex justify-end space-x-4">
                        <button
                            type="button"
                            onClick={handleSubmit}
                            className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        >
                            Submit
                        </button>
                        <button
                            type="button"
                            onClick={handleEditClick}
                            className="px-4 py-2 bg-gray-500 text-white rounded-md hover:bg-gray-600 focus:outline-none focus:ring-2 focus:ring-gray-500"
                        >
                            Cancel
                        </button>
                    </div>
                )}
            </form>
            {!isEditing && (
                <button
                    onClick={handleEditClick}
                    className="mt-4 px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                    Edit Profile
                </button>
            )}
        </div>
    );
}