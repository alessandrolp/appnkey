import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { LoginService } from 'src/app/layouts/login.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  @Output() toggleSideBarClick: EventEmitter<any> = new EventEmitter();

  constructor(private loginService : LoginService) { }

  ngOnInit(): void {
  }

  toggleSideBar() {
    this.toggleSideBarClick.emit();
    setTimeout(() => {
      window.dispatchEvent(
        new Event('resize')
      );
    }, 300);
  }

  onExit() {
    this.loginService.logout();
  }

}
