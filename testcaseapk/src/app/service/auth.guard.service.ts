import { Injectable, Inject } from '@angular/core';
import {
  CanActivate, Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  CanActivateChild,
  NavigationExtras,
  CanLoad, Route
} from '@angular/router';
import { Location } from '@angular/common';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

/**
 * Guard, checks access token availability and allows or disallows access to page,
 * and redirects out
 *
 * usage: { path: 'test', component: TestComponent, canActivate: [ AuthGuard ] }
 *
 * @export
 */

declare var $: any;
@Injectable()
export class AuthGuard implements CanActivate, CanActivateChild, CanLoad  {
  
  menus: any = [];
  userInfo: any = {};
  savedToast: any = {};
  toastPosition = "";
  toastType = "";

  readonly app_name: string = "TestCase";

  constructor(private _location: Location, private router: Router, private http: HttpClient) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    let url: string = state.url;
    let access = this.checkAccess(url, route);
    // console.log(access);
    return access;
    //return true;
  }

  canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    return this.canActivate(route, state);
    //return true;
  }

  canLoad(route: Route): boolean {
    let url = `/${route.path}`;
    return this.checkAccess(url);
  }

  checkAccess(url: string, route?: ActivatedRouteSnapshot): boolean {
    let menuData = this.GetMenu();
    let userInfo = this.GetUserInfo();
    let flag = false;
    if (url == userInfo.homeScreenUrl) {
      return true;
    }
    if (menuData.length == 0) {
      flag = false;
    }

    menuData.forEach(element => {
      element.childMenu.forEach(innerElement => {
        if (innerElement.url == url) {
          flag = true;
        }
      });
    });
    if (!flag) {


      this.logout().subscribe((data: any) => {

        if (data.code == "SUCCESS") {
          this.SetUserInfo(null);
          let lang = localStorage.getItem(this.getAppName() + "-" + "currentLanguage");
          localStorage.removeItem(this.getAppName() + "-" + "tokendetails");
          localStorage.clear();
          localStorage.setItem(this.getAppName() + "-" + "currentLanguage", lang);
          this.ShowToasts('', '', "You are not authorized.", 'Danger');
          this.router.navigate(['./auth/login']);
        }

      }, error => {
      }, () => {
      });

    }
    return flag;
  }

  public SetMenu(data) {
    this.menus = data;
    return true;
  }

  public GetMenu() {
    return this.menus;
  }

  public SetUserInfo(data) {
    this.userInfo = data;
    return true;
  }

  public GetUserInfo() {
    //this.userInfo = data;
    return this.userInfo;
  }

  public logout<T>(): Observable<T> {
    return this.http.delete<T>(`http://localhost:8811/psh-identity-service/user/oauth/token`);
  }

  getAppName() {
    return this.app_name;
  }

  ShowToasts(title: string, subtitle: string, message: string, type: string) {
    let iconName = ""
    //  let currentUserDetail = JSON.parse(localStorage.getItem("userDetails"));
    //  let currentConfigDetail = JSON.parse(localStorage.getItem("themeConfig"));
    if (type == "Success") {
        this.toastType = "bg-success";
        iconName = "fas fa-info-circle";
    } else if (type == "Info") {
        this.toastType = "bg-info";
        iconName = "fas fa-info-circle";
    } else if (type == "Warning") {
        this.toastType = "bg-warning";
        iconName = "fas fa-exclamation-circle";
    }
    else if (type == "Danger") {
        this.toastType = "bg-danger";
        iconName = "fas fa-exclamation-triangle";
    }
    if (this.savedToast.position != undefined) {
        if (this.savedToast.position == "TL") {
            this.toastPosition = "topLeft";
        } else if (this.savedToast.position == "TR") {
            this.toastPosition = "topRight";
        } else if (this.savedToast.position == "BL") {
            this.toastPosition = "bottomLeft";
        }
        else if (this.savedToast.position == "BR") {
            this.toastPosition = "bottomRight";
        }
        else if (this.savedToast.position == "TC") {
            this.toastPosition = "topCenter";
        }
        else if (this.savedToast.position == "BC") {
            this.toastPosition = "bottomCenter";
        }
    } else {
        this.toastPosition = "topCenter";
    }


    $(document).Toasts('create', {
        class: this.toastType,
        title: message,
        subtitle: '',
        position: this.toastPosition,
        icon: iconName,
        autohide: true,
        delay: 5000,
    })
    return 1;
  }

}
