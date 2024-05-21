'use client'
import { React, useEffect, useState } from 'react';
import Navigation from "@/components/Navigation";
import { Footer } from "@/components/Footer";
import Link from "next/link";
import { useRouter } from 'next/navigation';
import { useStore, isLoggedIn, isAdmin} from "@/tools/authentication";
import AccountSettings from "@/components/AccountSettings.js"
import {asyncApiRequest} from "@/tools/request";
import { Button } from "@/components/ui/button"

export default function Page() {
    const user = useStore((state) => [state.user, state.setUser]);
    const [activeComponent, setActiveComponent] = useState('default');
    const [userDetails, setUserDetails] = useState(null);

    if (!isLoggedIn()) {
        console.log("User is not logged in. Redirecting to login page.");
        useRouter().push("/");
    }

    useEffect(() => {
        const fetchUserDetails = async () => {
            if (user && user.email) {
                try {
                    console.log("Fetching user details for: ", user.email);
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
            default:
                return <p></p>;
        }
    };

    return (
        <div>
        {isLoggedIn() && (
        <div className="bg-[url('/temp-background-image-low.webp')] bg-cover bg-center">
            <Navigation />
            <section className="text-black">
                <p>
                    <Link href="/">Home</Link> &gt; <Link href="/profile">Profile</Link>
                </p>
            </section>
            <div className="flex items-center justify-center min-h-screen">
                <section className="m-2 p-2 bg-white h-dvh w-4/5 rounded flex">
                    <div className="flex flex-col w-1/4 h-full bg-red-200">
                    <Button onClick={() => handleClick('account')} className="py-2 px-4 bg-blue-500 text-white rounded">Account</Button>
                    <Button onClick={() => handleClick('myorders')} className="py-2 px-4 bg-blue-500 text-white rounded">My Orders</Button>
                    {isAdmin() && (
                        <>
                            <Button onClick={() => handleClick('editcars')} className="py-2 px-4 bg-blue-500 text-white rounded">Car Editor</Button>
                            <Button onClick={() => handleClick('viewusers')} className="py-2 px-4 bg-blue-500 text-white rounded">View Users</Button>
                            <Button onClick={() => handleClick('vieworders')} className="py-2 px-4 bg-blue-500 text-white rounded">View Orders</Button>
                        </>
                    )}

                    </div>
                    <div className="w-3/4 h-full bg-blue-200">
                        {renderComponent()}
                    </div>
                </section>
            </div>
            <Footer />
            </div>
        )};
        </div>
    );
};
