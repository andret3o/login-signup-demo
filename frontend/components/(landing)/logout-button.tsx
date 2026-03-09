"use client";

import { LogOut } from "lucide-react";
import { Button } from "../ui/button";
import { useAuth } from "@/context/AuthContext";

export function LogOutButton() {
  const { isLoggedIn, refreshAuth } = useAuth();

  const logout = async () => {
    const response = await fetch("/api/auth/logout", {
      method: "POST",
    });
    refreshAuth();
  };
  return (
    <Button variant="ghost" size="icon" onClick={logout} disabled={!isLoggedIn}>
      <LogOut />
    </Button>
  );
}
