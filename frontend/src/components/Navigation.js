import React from "react";
import Link from "next/link";
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
                        <Link href="/" style={{marginRight: '100px'}}>Home</Link>
                    </b>
                </p>
                <p>
                    <b>
                        <Link href="about" style={{marginRight: '100px'}}>About</Link>
                    </b>
                </p>
                <p>
                    <b>
                        <Link href="contact" style={{marginRight: '100px'}}>Contact</Link>
                    </b>
                </p>
                <p>
                    <b>
                        <button style={{marginRight: '100px'}}>Login</button>
                    </b>
                </p>
            </nav>

        </div>
    )
}

export default Navigation;
