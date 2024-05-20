'use client'
import { React, useEffect, useState } from 'react';
import Navigation from "@/components/Navigation";
import { Footer } from "@/components/Footer";
import Link from "next/link";
import {isAdmin, isUser, getAuthenticatedUser} from "@/tools/authentication.js"
import AdministratorSettings from "@/components/AdministratorSettings.js"
import AccountSettings from "@/components/AccountSettings.js"
import {asyncApiRequest} from "@/tools/request";

export default function Page() {
    const [user, setUser] = useState(null);
    const [activeComponent, setActiveComponent] = useState('default');
    const [userDetails, setUserDetails] = useState(null);

    useEffect(() => {
        const authenticatedUser = getAuthenticatedUser();
        setUser(authenticatedUser);
    }, []);

    useEffect(() => {
        const fetchUserDetails = async () => {
            if (user && user.email) {
                try {
                    const userDetails = await asyncApiRequest("GET", `/api/users/${user.email}`);
                    setUserDetails(userDetails);
                } catch (error) {
                    console.error("Error fetching user details: ", error);
                }
            }
        }

        fetchUserDetails()
    }, [user]);

    const handleClick = (componentName) => {
        setActiveComponent(componentName);
    };

    const renderComponent = () => {
        switch(activeComponent) {
            case 'account':
                return <AccountSettings userDetails={userDetails}/>;
            case 'administrator':
                return <AdministratorSettings />;
            default:
                return <p></p>;
        }
    };

    useEffect(() => {
        if (!isAdmin(user) && !isUser(user)) {
            console.log("log in");
        }
    }, [user]);



    return (
        <div className="bg-[url('/temp-background-image-low.webp')] bg-cover bg-center">
            <Navigation />
            <section className="text-black">
                <p>
                    <Link href="/">Home</Link> &gt; <Link href="/profile">Profile</Link>
                </p>
            </section>
            <div className="flex items-center justify-center min-h-screen">
                <section className="m-2 p-2 bg-white h-dvh w-4/5 rounded flex">
                    <div className="w-1/4 h-full bg-red-200">
                        <button onClick={() => handleClick('account')} className="py-2 px-4 bg-blue-500 text-white rounded">Account</button>
                        <button onClick={() => handleClick('administrator')} className="py-2 px-4 bg-blue-500 text-white rounded">Administrator</button>
                    </div>
                    <div className="w-3/4 h-full bg-blue-200">
                        {renderComponent()}
                    </div>
                </section>
            </div>
            <Footer />
        </div>
    );
};