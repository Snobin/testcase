import { Directive, ElementRef, HostListener, Renderer2 } from '@angular/core';

@Directive({
  selector: '[appResizable]'
})
export class ResizableDirective {
  private isResizing = false;

  constructor(private el: ElementRef, private renderer: Renderer2) {}

  @HostListener('mousedown', ['$event'])
  onMouseDown(event: MouseEvent) {
    if (this.isAtBorder(event)) {
      event.preventDefault();
      this.isResizing = true;

      const startX = event.clientX;
      const startY = event.clientY;

      const resizeElement = (e: MouseEvent) => {
        if (this.isResizing) {
          const newWidth = this.el.nativeElement.offsetWidth + (e.clientX - startX);
          const newHeight = this.el.nativeElement.offsetHeight + (e.clientY - startY);

          this.renderer.setStyle(this.el.nativeElement, 'width', `${newWidth}px`);
          this.renderer.setStyle(this.el.nativeElement, 'height', `${newHeight}px`);
        }
      };

      const stopResize = () => {
        this.isResizing = false;
        window.removeEventListener('mousemove', resizeElement);
        window.removeEventListener('mouseup', stopResize);
      };

      window.addEventListener('mousemove', resizeElement);
      window.addEventListener('mouseup', stopResize);
    }
  }

  private isAtBorder(event: MouseEvent): boolean {
    const borderSize = 10; // Set your desired border size

    const rect = this.el.nativeElement.getBoundingClientRect();

    return (
      event.clientX < rect.left + borderSize ||
      event.clientY < rect.top + borderSize ||
      event.clientX > rect.right - borderSize ||
      event.clientY > rect.bottom - borderSize
    );
  }
}
