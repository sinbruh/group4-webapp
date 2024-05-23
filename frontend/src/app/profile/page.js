'use client'
import { React, useEffect, useState } from 'react';
import Navigation from "@/components/Navigation";
import { Footer } from "@/components/Footer";
import Link from "next/link";
import {redirect, useRouter} from 'next/navigation';
import { useStore, isLoggedIn, isAdmin} from "@/tools/authentication";
import AccountSettings from "@/components/AccountSettings.js"
import {asyncApiRequest} from "@/tools/request";
import { Button } from "@/components/ui/button"
import {UserTable} from "@/components/UserTable";
import MyOrders from "@/components/MyOrders";
import ViewOrders from "@/components/ViewOrders";
import CarEditor from '@/components/CarEditor';
import ConfigurationEditor from '@/components/ConfigurationEditor';
import ProviderEditor from '@/components/ProviderEditor';
import Head from "next/head";

export default function Page() {
    const user = useStore((state) => state.user);
    const [activeComponent, setActiveComponent] = useState('default');
    const [userDetails, setUserDetails] = useState(null);

    if (!isLoggedIn()) {
        console.log("User is not logged in. Redirecting to login page.");
        redirect("/");
    }

    useEffect(() => {
        const fetchUserDetails = async () => {
            if (user) {
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
            case 'viewusers':
                return <UserTable />;
            case 'myorders':
                return <MyOrders userDetails={userDetails}/>;
            case 'vieworders':
                return <ViewOrders />;
            case 'editcars':
                return <CarEditor />;
            case 'editconfig':
                return <ConfigurationEditor />;
            case 'editprovider':
                return <ProviderEditor />;
            default:
                return userDetails ? <AccountSettings userDetails={userDetails}/> : null;
        }
    };

    return (
        <>
            <Head>
                <title>Profile Page</title>
            </Head>
            <div>
                {isLoggedIn() && (
                    <div className="bg-[url('/temp-background-image-low.webp')] bg-cover bg-center">
                        <Navigation/>
                        <section className="text-black">
                            <p>
                                <Link href="/">Home</Link> &gt; <Link href="/profile">Profile</Link>
                            </p>
                        </section>
                        <div className="flex flex-wrap items-center justify-center">
                            <section
                                className="mx-auto p-6 m-2 p-2 h-dvh bg-white max-w-full shadow-md rounded-md min-w-[60%] rounded flex-col overflow-auto">
                                <div className="flex flex-wrap gap-x-2  justify-center">
                                    <Button onClick={() => handleClick('account')}
                                            className="m-4 w-full md:w-auto md:grow-0 md:m-0">Account</Button>
                                    <Button onClick={() => handleClick('myorders')}
                                            className="m-4 w-full md:w-auto md:grow-0 md:m-0">My Orders</Button>
                                    {isAdmin() && (
                                        <>
                                            <Button onClick={() => handleClick('viewusers')}
                                                    className="m-4 w-full md:w-auto md:grow-0 md:m-0">View
                                                Users</Button>
                                            <Button onClick={() => handleClick('vieworders')}
                                                    className="m-4 w-full md:w-auto md:grow-0 md:m-0">View
                                                Orders</Button>
                                            <Button onClick={() => handleClick('editcars')}
                                                    className="m-4 w-full md:w-auto md:grow-0 md:m-0">Car
                                                Editor</Button>
                                            <Button onClick={() => handleClick('editconfig')}
                                                    className="m-4 w-full md:w-auto md:grow-0 md:m-0">Configuration
                                                Editor</Button>
                                            <Button onClick={() => handleClick('editprovider')}
                                                    className="m-4 w-full md:w-auto md:grow-0 md:m-0">Provider
                                                Editor</Button>
                                        </>
                                    )}

                                </div>
                                <div className=" flex items-center justify-center">
                                    {renderComponent()}
                                </div>
                            </section>
                        </div>
                        <Footer/>
                    </div>
                )};
            </div>
        </>
    );
};
