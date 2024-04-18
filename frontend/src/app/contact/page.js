import React from 'react';
import Navigation from "@/components/Navigation";
import {Footer} from "@/components/Footer";
import Link from "next/link";
import styles from "@/styles/style.css";

export default function Page() {
    return (
        <div>
            <Navigation />
            <section className={styles.breadcrumb}>
                <p>
                    <Link href="/">Home</Link> &gt; <Link href="/contact">Contact</Link>
                </p>
            </section>
            <section className={styles.contact}>
                <h1>Contact Us</h1>
                <p>
                    Have a question, concern, or feedback? We'd love to hear from you!
                    Feel free to reach out to us
                    using the contact information provided below, and we'll get back to you as soon as possible.
                </p>
            </section>
            <Footer />
        </div>
    )
}