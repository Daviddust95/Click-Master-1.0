import tkinter as tk
import pyautogui
import threading
from pynput import keyboard

class AutoclickerApp:
    def __init__(self, master):
        self.master = master
        master.title("Autoclicker")
        master.geometry("300x200")  # Define o tamanho da janela

        self.click_rate = 100  # Define a taxa de cliques em milissegundos
        self.is_clicking = False

        self.status_label = tk.Label(master, text="Status: Parado")
        self.status_label.pack(pady=10)

        self.start_button = tk.Button(master, text="Iniciar Autoclick (Pressione 'S')", command=self.start_autoclick)
        self.start_button.pack(pady=5)

        self.stop_button = tk.Button(master, text="Parar Autoclick (Pressione 'P')", command=self.stop_autoclick, state=tk.DISABLED)
        self.stop_button.pack(pady=5)

        self.autoclick_thread = None  # Thread para controlar o autoclick

        # Adiciona os eventos de teclado para iniciar e parar os cliques
        self.listener = keyboard.Listener(on_press=self.on_key_press, on_release=self.on_key_release)
        self.listener.start()

    def start_autoclick(self):
        self.is_clicking = True
        self.start_button.configure(state=tk.DISABLED)
        self.stop_button.configure(state=tk.NORMAL)
        self.status_label.configure(text="Status: Em Execução")

        self.autoclick_thread = threading.Thread(target=self.autoclick)
        self.autoclick_thread.start()

    def stop_autoclick(self):
        self.is_clicking = False
        self.start_button.configure(state=tk.NORMAL)
        self.stop_button.configure(state=tk.DISABLED)
        self.status_label.configure(text="Status: Parado")

        if self.autoclick_thread and self.autoclick_thread.is_alive():
            self.autoclick_thread.join()

    def on_key_press(self, key):
        try:
            if key.char == "s":
                self.start_autoclick()
            elif key.char == "p":
                self.stop_autoclick()
        except AttributeError:
            pass

    def on_key_release(self, key):
        pass

    def autoclick(self):
        while self.is_clicking:
            pyautogui.click()
            pyautogui.PAUSE = self.click_rate / 1000

root = tk.Tk()
app = AutoclickerApp(root)
root.mainloop()
