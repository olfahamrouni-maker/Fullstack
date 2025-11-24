import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { Footer } from "./footer/footer";
import { Header } from "./header/header";
import { AuthService } from './service/auth-service';


@Component({
  selector: 'app-root',
  imports: [Footer, RouterOutlet, Header],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('domo');
  isConnected  = false;
  constructor(private auth: AuthService){
  this.isConnected = this.auth.isLoggedIn();
  }
}
