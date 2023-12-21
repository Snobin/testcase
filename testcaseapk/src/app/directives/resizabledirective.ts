import { Directive, ElementRef, HostListener, Renderer2 } from '@angular/core';

@Directive({
  selector: '[appResizable]'
})
export class ResizableDirective {
  private isResizing = false;

  constructor(private el: ElementRef, private renderer: Renderer2) {}

  @HostListener('mousedown', ['$event'])
  onMouseDown(event: MouseEvent): void {
    event.preventDefault();
    this.isResizing = true;

    const mouseMoveListener = this.renderer.listen('document', 'mousemove', (e: MouseEvent) => {
      this.onMouseMove(e);
    });

    const mouseUpListener = this.renderer.listen('document', 'mouseup', () => {
      this.onMouseUp(mouseMoveListener);
    });
  }

  private onMouseMove(event: MouseEvent): void {
    if (this.isResizing) {
      const mouseY = event.clientY;
      const containerRect = this.el.nativeElement.getBoundingClientRect();
      const newHeight = mouseY - containerRect.top;

      this.renderer.setStyle(this.el.nativeElement, 'height', `${newHeight}px`);
      this.renderer.setStyle(this.el.nativeElement.nextElementSibling, 'height', `calc(100% - ${newHeight}px)`);
    }
  }

  private onMouseUp(listener: () => void): void {
    this.isResizing = false;
    listener();
  }
}