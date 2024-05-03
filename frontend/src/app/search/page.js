import styles from "./search.module.css";
import React from 'react';
import Navigation from "@/components/Navigation";
import {Footer} from "@/components/Footer";
import Image from "next/image";
import carImage from "@/img/cars/BMW-M3.jpg";
import Link from "next/link";
import FilterBar from "@/components/FilterBar";
import {Button} from "@/components/ui/button";

export default function Search() {
    return (
        <>
            <Navigation />
            <section className={styles.breadcrumb}>
                <p>
                    <Link href="/">Home</Link> &gt; <Link href="/search">Search</Link>
                </p>
            </section>
            <FilterBar />
            <section className={styles.hero}>
                <section className={styles.cards}>
                    <div className={styles.card} id={"mercedez"}>
                        <Image src={carImage} alt={"car img"} width={300} height={200} />
                        <div className={"card-info"}>
                            <h2>mercedez</h2>
                            <p>Price: 500 NOK/day</p>
                            <p>Location: Ålesund</p>
                        </div>
                    </div>
                    <div className={styles.card} id={"bmw"}>
                        <Image src={carImage} alt="car img" width={300} height={200} />
                        <div className={"card-info"}>
                            <h2>bmw</h2>
                            <p>Price: 500 NOK/day</p>
                            <p>Location: Ålesund</p>
                        </div>
                    </div>
                    <div className={styles.card} id={"audi"}>
                        <Image src={carImage} alt={"car img"} width={300} height={200} />
                        <div className={"card-info"}>
                            <h2>audi</h2>
                            <p>Price: 500 NOK/day</p>
                            <p>Location: Ålesund</p>
                        </div>
                    </div>
                    <div className={styles.card} id={"audi"}>
                        <Image src={carImage} alt={"car img"} width={300} height={200} />
                        <div className={"card-info"}>
                            <h2>audi</h2>
                            <p>Price: 500 NOK/day</p>
                            <p>Location: Ålesund</p>
                        </div>
                    </div>
                </section>
                <div className={styles.expandedCard} id={"expandedCard"}>
                    <Image src={carImage} alt={"car img"} width={300} height={200} />
                    <div className={styles.cardi}>
                        <h2>mercedez</h2>
                        <p>Price: 500 NOK/day</p>
                        <p>Location: Ålesund</p>
                        <p>Owner: John Doe</p>
                        <p>Phone: 12345678</p>
                        <p>Email: matsern@email.com</p>
                        <Button>Book</Button>
                    </div>
                </div>
            </section>
            <Footer />
        </>
    );
};
