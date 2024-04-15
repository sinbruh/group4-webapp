import React from "react";
import Link from "next/Link";
import logo from "@/img/temp-logo.png";
import Image from "next/image";
export function Navigation() {
    return (
        <div id="nav-container">
            <header>
                <Link href="/">
                    <Image
                        src={logo}
                        alt="Temporary logo"
                        width={32}
                        height={32}
                    />
                </Link>
                <Link href="/">
                    <h1>Rental Roulette</h1>
                </Link>
            </header>
            <nav>
                <p>
                    <b>
                        <Link href="/">Home</Link>
                    </b>
                </p>
                <p>
                    <b>
                        <Link href="about">About</Link>
                    </b>
                </p>
                <p>
                    <b>
                        <Link href="contact">Contact</Link>
                    </b>
                </p>
            </nav>
            {/* User icon */}
            <svg
                id="user-icon"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                strokeWidth="1.5"
                stroke="#000000"
                className="w-6 h-6"
                width={60}
                height={60}
            >
                <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="M17.982 18.725A7.488 7.488 0 0 0 12 15.75a7.488 7.488 0 0 0-5.982 2.975m11.963 0a9 9 0 1 0-11.963 0m11.963 0A8.966 8.966 0 0 1 12 21a8.966 8.966 0 0 1-5.982-2.275M15 9.75a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z"
                />
            </svg>
        </div>
    )
}

export default Navigation;