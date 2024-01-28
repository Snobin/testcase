import { Injectable, HostListener } from '@angular/core';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root',
})
export class FullScreenService {

  variable:number=0;

  requestFullScreen(): void {
    const element = document.documentElement as any;

    if (element.requestFullscreen) {
      element.requestFullscreen();
    } else if (element.mozRequestFullScreen) {
      element.mozRequestFullScreen();
    } else if (element.webkitRequestFullscreen) {
      element.webkitRequestFullscreen();
    } else if (element.msRequestFullscreen) {
      element.msRequestFullscreen();
    }
  }

  onVisibilityChange(hidden: boolean): void {
    if (hidden) {
      this.variable++;
      Swal.fire('oo you are smart, dont repeat it again' );
      if(this.variable==3){
        Swal.fire('you are disqualified' );
       }
    } else {
      // Tab is visible again
    }
  }

  onKeyDown(event: KeyboardEvent): void {
    if (event.ctrlKey && event.key === 'Tab') {
      this.variable++;
      event.preventDefault(); // Prevent default tab-switching behavior
      Swal.fire('oo you are smart, dont repeat it again' );
     if(this.variable==3){
      Swal.fire('you are disqualified' );
     }
    }
  }
}