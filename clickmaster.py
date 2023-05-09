import pyautogui
import time

# Define a função para gravar a macro
def gravar_macro():
    print("Gravando macro...")
    time.sleep(2) # aguarda 2 segundos antes de começar a gravar
    pyautogui.PAUSE = 1 # pausa por 1 segundo entre cada ação
    pyautogui.FAILSAFE = True # ativa a opção de interromper a execução se mover o mouse para o canto superior esquerdo

    # Inicia a gravação
    actions = []
    while True:
        x, y = pyautogui.position()
        action = {"action": "click", "x": x, "y": y, "button": "left"}
        actions.append(action)
        time.sleep(0.5) # aguarda 0,5 segundos entre cada clique

        if len(actions) > 10:
            break

    # Salva as ações em um arquivo
    with open("macro.json", "w") as f:
        f.write(str(actions))

    print("Macro salva com sucesso!")

# Define a função para executar a macro
def executar_macro():
    print("Executando macro...")
    time.sleep(2) # aguarda 2 segundos antes de começar a executar

    # Carrega as ações do arquivo
    with open("macro.json", "r") as f:
        actions = eval(f.read())

    # Executa as ações
    for action in actions:
        if action["action"] == "click":
            pyautogui.click(action["x"], action["y"], button=action["button"])
        time.sleep(1) # aguarda 1 segundo entre cada ação

    print("Macro executada com sucesso!")

# Menu principal
while True:
    print("1. Gravar macro")
    print("2. Executar macro")
    print("3. Sair")
    opcao = int(input("Escolha uma opção: "))

    if opcao == 1:
        gravar_macro()
    elif opcao == 2:
        executar_macro()
    elif opcao == 3:
        break
    else:
        print("Opção inválida!")
