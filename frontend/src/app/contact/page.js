import React from 'react';
import Navigation from "@/components/Navigation";
import { Footer } from "@/components/Footer";
import Link from "next/link";

export default function Page() {
    return (
        <div>
            <Navigation />
            <section className="text-black">
                <p>
                    <Link href="/">Home</Link> &gt; <Link href="/contact">Contact</Link>
                </p>
            </section>
            <section className="m-2 p-2 bg-white h-dvh rounded">
                <h1 className="text-xl">Contact Us</h1>
                <p>
                    Have a question, concern, or feedback? We'd love to hear from you!
                    Feel free to reach out to us
                    using the contact information provided below, and we'll get back to you as soon as possible.
                </p>
                <h2>Technical Support</h2>
                <p>
                    Incase of any technical issues, please contact our support team at 999 99 999.
                    Our team is available 24/7 to assist you with any troubleshooting or inquiries
                    if you encounter any difficulties while using our platform.
                </p>
                <h3>Media Inqueries</h3>
                <p>
                    For media inquiries, please contact our press team at 999 99 999.
                    Our team is available Monday through Friday, from 9:00 AM to 5:00 PM.
                </p>
            </section>
            <Footer />
        </div>
    )
}
