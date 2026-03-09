import { ModeToggle } from "@/components/mode-toggle";
import { LoginDialog } from "@/components/(landing)/login-dialog";
import { SignupDialog } from "@/components/(landing)/signup-dialog";
import { LoginBadge } from "@/components/(landing)/login-badge";
import { Button } from "@/components/ui/button";
import { LogOut } from "lucide-react";
import { LogOutButton } from "@/components/(landing)/logout-button";

export default function Home() {
  return (
    <main className="min-h-screen flex flex-col items-center justify-center gap-2">
      <section className="flex flex-col gap-6">
        <div className="flex items-center justify-center">
          <LoginBadge />
        </div>

        <div className="flex items-center justify-center gap-2">
          <LoginDialog />
          <SignupDialog />
          <LogOutButton />
          <ModeToggle />
        </div>
      </section>
    </main>
  );
}
