import React from "react";
import styles from "./index-style.module.css";
import Link from "next/link";
import Navigation from "@/components/Navigation";
import {Footer} from "@/components/Footer";

export default function Home() {
  return (
    <>
      <Navigation />
        <div className={styles.mainContainer}>
        <section>
          <input
              className={styles.textField}
              type="text"
              placeholder="Location"
              list="locations"
          />
          <datalist id="locations">
            <option>Ålesund</option>
          </datalist>
          <div className="field">
            <label>From</label>
            <input type="date"/>
          </div>
          <div className="field">
            <label>From</label>
            <input type="time"/>
          </div>
          <div className="field">
            <label>To</label>
            <input type="date"/>
          </div>
          <div className="field">
            <label>To</label>
            <input type="time"/>
          </div>
          <Link href="/search">
            <div className={styles.icon}>
              {/* Search icon */}
              <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  strokeWidth="1.5"
                  stroke="#FFFFFF"
                  className="w-6 h-6"
                  width={38}
                  height={38}
              >
                <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z"
                />
              </svg>
            </div>
          </Link>
        </section>
      </div>
  <div className={styles.sideContainer}>
    <div className={styles.info}>
    <div>
      <h2>How it works</h2>
      <p>
        Rental Roulette is a service that helps you find a car to rent. You
        simply enter your location and the dates you need the car, and we will
        find the best car for you. We will show you the car and the price, and
        you can book it right away.
      </p>
    </div>
    <div>
      <h2>Our reviews</h2>
      <p>
        "I was very happy with the car I got from Rental Roulette. It was a
        great car and the price was very good." - John Doe
      </p>
      <p>
        "The service was very easy to use and I got a great car for my
        vacation." - Jane Doe
      </p>
    </div>
    </div>
    <Footer />
    </div>
    </>
);
}
