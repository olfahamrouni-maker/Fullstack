import { Component } from '@angular/core';
import { AuthService } from '../service/auth-service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  credentials = { username: '', password: '' ,role:''};
  error = '';
  isConnected = false;
  constructor(private auth: AuthService, private router: Router) {}
  
  onSubmit() {
    this.auth.login(this.credentials).subscribe({
      next: () => {this.router.navigate(['/home']);},
      error: () => this.error = "Identifiants incorrects"
    });
  }
}
