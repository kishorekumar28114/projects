"use client";
import { useRouter } from "next/navigation";
import AuthForm from "../components/AuthForm";
import styles from "./page.module.css";

export default function Home() {
  const router = useRouter();
  const handleAuthSuccess = (username) => {
    router.push(`/dashboard/${username}`);
  };
  return (
    <div className={styles.Loginpage}>
      <AuthForm onAuthSuccess={handleAuthSuccess} />
    </div>
  );
}
