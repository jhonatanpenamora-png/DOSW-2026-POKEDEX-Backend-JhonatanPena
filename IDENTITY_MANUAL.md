# Manual de Identidad Visual — PokeData OS

> **Versión:** 1.0  
> **Proyecto:** API REST Pokédex — Arquitectura MVC DOSW 2026  
> **Fecha:** Julio 2026

---

## Índice

1. [Nombre y Logotipo](#1-nombre-y-logotipo)
2. [Paleta de Colores](#2-paleta-de-colores)
3. [Tipografía](#3-tipografía)
4. [Iconografía y Estilo Gráfico](#4-iconografía-y-estilo-gráfico)
5. [Reglas de Uso de la Marca](#5-reglas-de-uso-de-la-marca)
6. [Mascota Representativa](#6-mascota-representativa)
7. [Aplicaciones](#7-aplicaciones)
8. [Tonos de Voz](#8-tonos-de-voz)
9. [Archivos](#9-archivos)

---

## 1. Nombre y Logotipo

### 1.1 Nombre

**PokeData OS** — La "O" de "OS" está representada por una Pokéball minimalista con un haz de luz digital o gráfico de barras integrado en su diseño.

### 1.2 Logotipo Principal

El logotipo consiste en la palabra **PokeData** en tipografía geométrica (Orbitron Bold), seguida de "OS" donde la letra **O** es reemplazada por el isotipo de Pokéball digital.

| Elemento | Descripción |
|---|---|
| **Texto** | "PokeData OS" en Orbitron Bold |
| **Isotipo** | Pokéball minimalista con gráfico de barras/haz digital integrado |
| **Disposición** | Horizontal: texto + isotipo |
| **Uso principal** | README, headers, documentación, Swagger UI |

### 1.3 Versiones del Logo

| Versión | Descripción | Uso |
|---|---|---|
| **Principal a color** | Logotipo completo sobre fondo oscuro (Black Cyber #121214) | README, app, documentación digital |
| **Monocromática blanca** | Logotipo en blanco (#FFFFFF) sobre fondo oscuro | Fondos oscuros, Docker, terminal |
| **Monocromática negra** | Logotipo en Black Cyber (#121214) sobre fondo claro | Documentación impresa, fondos claros |
| **Simplificada (isotipo)** | Solo la Pokéball digital | Favicon, avatar, app icon, GitHub |

### 1.4 Área de Resguardo

| Variante | Mínimo |
|---|---|
| Horizontal | Espacio equivalente al tamaño de la "O" del logotipo alrededor |
| Isotipo | 15px alrededor del círculo |

### 1.5 Tamaño Mínimo

| Variante | Mínimo |
|---|---|
| Horizontal (digital) | 200px de ancho |
| Isotipo (digital) | 48×48px |
| Impreso | 30mm de ancho |

---

## 2. Paleta de Colores

Para alejarse del rojo saturado clásico y darle un enfoque **tecnológico y de software ("OS")**, la paleta se basa en tonos oscuros con acentos neón de alta visibilidad.

### 2.1 Colores Primarios

| Muestra | Nombre | Hex | RGB | Uso |
|---|---|---|---|---|
| ⚫ | **Black Cyber** | `#121214` | `rgb(18, 18, 20)` | Fondo principal de la aplicación (modo oscuro) |
| 🔴 | **Red Rotom** | `#FF3E3E` | `rgb(255, 62, 62)` | Esencia Pokémon con toque energético y digital. Botones, badges, headers |

### 2.2 Colores Secundarios

| Muestra | Nombre | Hex | RGB | Uso |
|---|---|---|---|---|
| 🔵 | **Azul Eléctrico** | `#00E5FF` | `rgb(0, 229, 255)` | Datos, tecnología, pantallas holográficas. Gráficos, stats, filtros activos, links |
| ⚪ | **Blanco Puro** | `#FFFFFF` | `rgb(255, 255, 255)` | Texto principal sobre fondos oscuros |
| ⚘ | **Gris Ceniza** | `#E1E1E6` | `rgb(225, 225, 230)` | Textos secundarios, borders, contenedores secundarios |

### 2.3 Colores de Estado

| Muestra | Nombre | Hex | RGB | Uso |
|---|---|---|---|---|
| 🟢 | **Data Green** | `#00C853` | `rgb(0, 200, 83)` | Éxito, estados "UP", health checks |
| 🟡 | **Warning Yellow** | `#FFC107` | `rgb(255, 193, 7)` | Advertencias, estados degradados |
| 🔴 | **Error Glitch** | `#D32F2F` | `rgb(211, 47, 47)` | Errores graves, alerts destructivos |

### 2.4 Gradientes Recomendados

| Uso | Gradiente |
|---|---|
| **Background de login** | `linear-gradient(135deg, #121214 0%, #1a1a2e 50%, #121214 100%)` |
| **Header principal** | `linear-gradient(90deg, #FF3E3E 0%, #121214 100%)` |
| **Cards con glow** | `box-shadow: 0 0 20px rgba(0, 229, 255, 0.15)` |

### 2.5 Proporción de Uso

| Color | % en UI | Uso principal |
|---|---|---|
| Black Cyber (#121214) | ~60% | Fondos, contenedores |
| Red Rotom (#FF3E3E) | ~15% | Headers, botones, badges tipo Pokémon |
| Azul Eléctrico (#00E5FF) | ~10% | Acentos, hover, gráficos, links |
| Blanco (#FFFFFF) | ~10% | Texto principal |
| Gris Ceniza (#E1E1E6) | ~5% | Textos secundarios, bordes, metadata |

### 2.6 Accesibilidad

| Combinación | Ratio | Rating |
|---|---|---|
| Blanco sobre Black Cyber | 17.4:1 | ✅ AAA |
| Red Rotom sobre Black Cyber | 7.2:1 | ✅ AA (textos grandes) |
| Azul Eléctrico sobre Black Cyber | 8.1:1 | ✅ AA (textos grandes) |
| Gris Ceniza sobre Black Cyber | 7.5:1 | ✅ AA |
| Red Rotom sobre Blanco | 4.5:1 | ✅ AA (textos grandes) |

> **Nota:** Red Rotom (#FF3E3E) y Azul Eléctrico (#00E5FF) se usan exclusivamente para titulares, botones y acentos — nunca para texto corporal.  
> Para texto legible, usar Blanco (#FFFFFF) sobre fondo oscuro o Gris Ceniza (#E1E1E6) para metadata secundaria.

---

## 3. Tipografía

### 3.1 Familia Principal (Títulos y Encabezados) — Orbitron / Rajdhani

Fuentes de estilo futurista, geométrico y tecnológico. Se usan **exclusivamente en mayúsculas** para nombres de Pokémon, títulos de secciones y números de la Pokédex.

| Variante | Peso | Uso |
|---|---|---|
| Orbitron Black | 900 | Logotipo "PokeData OS", display |
| Orbitron Bold | 700 | Títulos principales (h1, h2) |
| Orbitron SemiBold | 600 | Subtítulos, secciones |
| Rajdhani Medium | 500 | Números de Pokédex, etiquetas |

```css
/* Títulos y encabezados */
font-family: 'Orbitron', 'Rajdhani', 'Segoe UI', system-ui, sans-serif;
```

### 3.2 Familia Secundaria (Texto y Descripciones) — Inter / Roboto

Se usa para descripciones de la Pokédex, datos de habilidades, flujos de usuario y configuraciones del perfil.

| Variante | Peso | Uso |
|---|---|---|
| Inter Regular | 400 | Cuerpo de texto, descripciones |
| Inter Medium | 500 | Subtítulos, metadata, botones |
| Inter SemiBold | 600 | Énfasis, datos destacados |
| Inter Bold | 700 | Encabezados secundarios (h3, h4) |

```css
/* Texto de lectura y descripciones */
font-family: 'Inter', 'Roboto', 'Segoe UI', system-ui, -apple-system, sans-serif;
```

### 3.3 Stack Completo

| Contexto | Fuente |
|---|---|
| Logotipo "PokeData OS" | Orbitron Black 900 |
| Nombres de Pokémon | Orbitron Bold 700, mayúsculas |
| Títulos de sección (h1, h2) | Orbitron Bold 700 |
| Subtítulos (h3, h4) | Inter Bold 700 |
| Body text, descripciones | Inter Regular 400 |
| Metadata, stats, tags | Inter Medium 500 |
| Código, endpoints, ejemplos | JetBrains Mono (o Fira Code, Cascadia Code) |

### 3.4 Tamaños y Jerarquía

| Nivel | Tamaño | Peso | Tracking |
|---|---|---|---|
| Display (logotipo) | 48px / 3rem | Orbitron Black | -2% |
| H1 | 36px / 2.25rem | Orbitron Bold | -1% |
| H2 | 28px / 1.75rem | Orbitron SemiBold | -0.5% |
| H3 | 22px / 1.375rem | Inter Bold | 0% |
| H4 | 18px / 1.125rem | Inter SemiBold | 0% |
| Body | 16px / 1rem | Inter Regular | 0% |
| Small | 14px / 0.875rem | Inter Medium | 0.5% |
| Code | 14px / 0.875rem | JetBrains Mono Regular | — |
| Caption | 12px / 0.75rem | Inter Medium | 1% |

---

## 4. Iconografía y Estilo Gráfico

### 4.1 Estilo General

| Atributo | Valor |
|---|---|
| **Modo** | Dark Mode (oscuro) obligatorio |
| **Estética** | Cyberpunk / Sci-Fi limpio — no sucio, no distópico |
| **Fondo base** | Black Cyber (#121214) |
| **Cards** | Bordes redondeados (8px), fondo semitransparente (efecto cristal/glassmorphism), borde sutil |
| **Efectos** | Glow sutiles en elementos seleccionados (box-shadow con Azul Eléctrico) |
| **Transiciones** | 0.2s ease en hover, 0.3s ease en modales |

### 4.2 Iconografía

| Tipo | Estilo |
|---|---|
| **Iconos de interfaz** | Líneas delgadas (outline icons), vectoriales, grosor 1.5-2px |
| **Iconos de búsqueda, filtros, ajustes** | Mismo grosor de línea y estilo plano (flat) |
| **Tipos de Pokémon** | Píldoras de color con iconos minimalistas estilizados en su interior |

**Colores de badges por tipo Pokémon (estilo neón):**

| Tipo | Hex | Tipo | Hex |
|---|---|---|---|
| Eléctrico | `#FFD700` | Fuego | `#FF6B6B` |
| Agua | `#4FC3F7` | Planta | `#66BB6A` |
| Psíquico | `#F48FB1` | Fantasma | `#9575CD` |
| Normal | `#BDBDBD` | Veneno | `#AB47BC` |
| Volador | `#90CAF9` | Dragón | `#7E57C2` |
| Hielo | `#81D4FA` | Lucha | `#FF7043` |
| Tierra | `#A1887F` | Roca | `#8D6E63` |
| Bicho | `#8BC34A` | Siniestro | `#757575` |
| Acero | `#90A4AE` | Hada | `#F8BBD0` |

### 4.3 Glassmorphism (efecto cristal)

```css
/* Card estándar */
.card {
  background: rgba(255, 255, 255, 0.04);
  backdrop-filter: blur(12px);
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 8px;
}

/* Card hover */
.card:hover {
  border-color: rgba(0, 229, 255, 0.3);
  box-shadow: 0 0 24px rgba(0, 229, 255, 0.1);
  transform: translateY(-3px);
}
```

---

## 5. Reglas de Uso de la Marca

### 5.1 Usos Correctos

| Contexto | Variante |
|---|---|
| ✅ Fondos oscuros (Black Cyber #121214, #1a1a2e) | Versión principal a color o versión monocromática blanca |
| ✅ Fondos muy oscuros (negro puro #000000) | Versión principal a color |
| ✅ Documentación impresa (fondo blanco) | Versión monocromática negra (tipografía en Black Cyber) |
| ✅ Favicon / app icon | Versión simplificada (isotipo) |
| ✅ GitHub avatar | Isotipo o logotipo completo sobre fondo Black Cyber |

### 5.2 Usos Incorrectos ❌

- ❌ **No** estirar, rotar o deformar las proporciones del logotipo
- ❌ **No** cambiar los colores de la paleta oficial (ej: fondo verde, logotipo amarillo)
- ❌ **No** aplicar sombras paralelas densas o efectos de relieve (bevel) tridimensionales
- ❌ **No** separar el isotipo del texto en la versión horizontal
- ❌ **No** usar sobre fondos que compitan con el contraste (rojos saturados, imágenes texturadas)
- ❌ **No** agregar gradientes externos al logotipo
- ❌ **No** usar la versión a color sobre fondos claros (usar la monocromática negra)

### 5.3 Fondos Permitidos vs. Prohibidos

| Fondo | ¿Permitido? | Variante recomendada |
|---|---|---|
| Black Cyber (#121214) | ✅ | Principal a color o blanca |
| #1a1a2e (oscuro azulado) | ✅ | Principal a color |
| Blanco (#FFFFFF) | ✅ | Monocromática negra |
| Gris Ceniza (#E1E1E6) | ✅ | Monocromática negra |
| Red Rotom (#FF3E3E) | ❌ | Pierde contraste |
| Imágenes texturadas | ❌ | Ninguna |

---

## 6. Mascota Representativa

### 6.1 Mascota Oficial: Rotom (Forma Catálogo / Pokédex)

![Rotom Pokédex Form](https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/479.png)

### 6.2 Justificación

Rotom es un Pokémon de tipo **Eléctrico/Fantasma** conocido por poseer aparatos tecnológicos. Su naturaleza digital, su cuerpo de plasma y su conexión directa con las Pokédex de generaciones recientes encajan perfectamente con el concepto de un software analítico y moderno como **PokeData OS**.

### 6.3 Atributos Rotom vs. PokeData OS

| Rotom | PokeData OS |
|---|---|
| Eléctrico (energía, datos) | API (datos, endpoints) |
| Fantasma (intangible, digital) | Cloud / servidores remotos |
| Posee aparatos electrónicos | Arquitectura MVC, Docker |
| Plasma brillante (cibernético) | Azul Eléctrico neón (#00E5FF) |
| Forma Pokédex (conexión Pokémon) | Catálogo Pokémon completo |

### 6.4 Uso de la Mascota

| Contexto | Uso |
|---|---|
| README header | Rotom como avatar o icono decorativo |
| Página de error 404 | Rotom con texto "Vínculo perdido..." |
| Loading / splash screen | Rotom rotando con glow eléctrico |
| Documentación técnica | Rotom como separador visual entre secciones |
| Swagger UI | Rotom en el logo de la documentación |

---

## 7. Aplicaciones

### 7.1 README / Documentación

```
┌─────────────────────────────────────────────────────────┐
│  🔴═══════════════════════════════════════════════════  │
│  ║  PokeData OS — Pokédex REST API                     ║
│  ║  Arquitectura MVC | Java 21 | Spring Boot 3.3.5     ║
│  🔵═══════════════════════════════════════════════════  │
│                                                         │
│  ⚡ Descripción en texto blanco sobre fondo oscuro...   │
│                                                         │
│  ┌─ Código ──────────────────────────────────────────┐  │
│  │ $env:JWT_SECRET = "..."                           │  │
│  └───────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────┘
```

- **Fondo**: Black Cyber (#121214)
- **Header**: Barra con gradiente Red Rotom → Black Cyber
- **Títulos**: Blanco (#FFFFFF) en Orbitron Bold
- **Código**: Bloque con fondo semitransparente, JetBrains Mono
- **Links**: Azul Eléctrico (#00E5FF)
- **Borders**: Gris Ceniza (#E1E1E6) con 1px

### 7.2 Swagger UI

```yaml
# Configuración OpenAPI
info:
  title: "PokeData OS API"
  description: "API REST de la Pokédex — Arquitectura MVC — DOSW 2026"
  contact:
    name: "Rotom (Mascota Oficial)"
```

- Personalizar CSS de Swagger con Red Rotom + Black Cyber si es posible
- Bearer JWT scheme configurado por defecto

### 7.3 Docker / Terminal

```
 ┌─────────────────────────────────────────────────┐
 │ ⚡ pokedex-api  │  Port 8080 (http)              │
 │ ⚡ postgres     │  Port 5432                     │
 │ ⚡ mongo        │  Port 27017                    │
 └─────────────────────────────────────────────────┘
```

- Fondo de terminal: Black Cyber (#121214) o Charcoal
- Prompt: Azul Eléctrico (#00E5FF)
- Output: Blanco (#FFFFFF)
- Indicador de servicio: ⚡ (rayo Rotom)

### 7.4 GitHub Repository

| Elemento | Aplicación |
|---|---|
| Avatar del repo | Isotipo Pokéball digital sobre fondo Black Cyber |
| README | Logotipo principal + badge con cobertura JaCoCo |
| Badges (build, tests) | Azul Eléctrico (#00E5FF) como color base |
| Topics | `pokedex` `spring-boot` `java21` `rest-api` `mvc` `oauth2` `docker` |
| About section | "⚡ PokeData OS — Pokédex REST API con Google OAuth2" |

### 7.5 Presentación Académica

| Elemento | Aplicación |
|---|---|
| Slide title | Orbitron Bold 36px, Blanco sobre fondo Black Cyber |
| Slide body | Inter Regular 18px, Gris Ceniza (#E1E1E6) |
| Código | JetBrains Mono 14px en recuadro semitransparente |
| Diagramas | Mantener esquema: Red Rotom para API, Azul Eléctrico para datos |
| Footer | Isotipo pequeño + "PokeData OS — DOSW 2026" |
| Transiciones | Hacia la derecha, estilo "presentación de producto" |

### 7.6 API Response Headers

```json
{
  "Content-Type": "application/json",
  "X-Application-Name": "PokeData OS",
  "X-API-Version": "1.0.0",
  "X-Powered-By": "Rotom ⚡"
}
```

---

## 8. Tonos de Voz

### 8.1 Documentación Técnica (README, Swagger, Javadoc)

| Atributo | Valor |
|---|---|
| **Lenguaje** | Español neutro profesional |
| **Tono** | Claro, preciso, directo |
| **Inspiración** | Manual técnico de producto, no blog |

> **Ejemplo:** "Retorna lista paginada de Pokémon filtrada por tipo, región y generación. Cada Pokémon incluye estadísticas base, habilidades y cadena evolutiva."

### 8.2 Código y Commits

| Atributo | Valor |
|---|---|
| **Lenguaje** | Inglés (convención universal) |
| **Tono** | Descriptivo, imperativo |
| **Estilo** | Conventional Commits |

> **Ejemplo:** `feat(auth): add Google OAuth2 login handler`

### 8.3 UI / Mensajes al Usuario

| Atributo | Valor |
|---|---|
| **Lenguaje** | Español neutro |
| **Tono** | Amable pero técnico — frío sin ser robótico |

| Contexto | Mensaje |
|---|---|
| Sesión expirada | "Tu sesión expiró. Iniciá sesión de nuevo." |
| Error de red | "No se pudo conectar con el servidor. Verificá tu conexión." |
| Favorito agregado | "Agregado a favoritos" |
| Equipo guardado | "Equipo guardado correctamente" |

### 8.4 Vocabulario

| ❌ Evitar | ✅ Usar |
|---|---|
| "Hola" / "Hey" / "Bienvenido" | Nada o un código de estado |
| "Oops" / "Ups" / "Algo salió mal" | "Error interno del servidor" |
| Emojis en respuestas API | Solo códigos HTTP |
| Slang o jerga callejera | Lenguaje técnico preciso |
| "Simplemente" / "Fácilmente" | Directo al punto |

---

## 9. Archivos

### 9.1 Ubicación

Todos los activos de marca se encuentran en `assets/`:

| Archivo | Descripción |
|---|---|
| `assets/logo/img.png` | Logotipo principal (834×833px) |
| `assets/logo/pokedata-os-horizontal.svg` | Logotipo horizontal (texto + isotipo) |
| `assets/logo/pokedata-os-icon.svg` | Isotipo solo (Pokéball digital) |
| `assets/logo/pokedata-os-white.svg` | Versión monocromática blanca |
| `assets/mascot/rotom-pokedex.png` | Rotom forma Pokédex |
| `assets/mascot/rotom-icon.svg` | Rotom simplificado para iconos |

### 9.2 Formatos Requeridos

| Formato | Uso |
|---|---|
| SVG | Web, documentación, escalable |
| PNG 512×512 | App icon, GitHub avatar |
| PNG 192×192 | Favicon |
| PNG 32×32 | Favicon legacy (ICO también aceptable) |

### 9.3 Paleta de Colores (Exportación)

```json
{
  "colors": {
    "blackCyber": "#121214",
    "redRotom": "#FF3E3E",
    "azulElectrico": "#00E5FF",
    "blancoPuro": "#FFFFFF",
    "grisCeniza": "#E1E1E6",
    "dataGreen": "#00C853",
    "warningYellow": "#FFC107",
    "errorGlitch": "#D32F2F"
  },
  "gradients": {
    "bgLogin": "linear-gradient(135deg, #121214 0%, #1a1a2e 50%, #121214 100%)",
    "header": "linear-gradient(90deg, #FF3E3E 0%, #121214 100%)"
  }
}
```

---

## Resumen Rápido

| Elemento | Valor |
|---|---|
| **Marca** | PokeData OS |
| **Estética** | Dark Mode | Cyberpunk limpio | Glassmorphism |
| **Colores** | ⚫ #121214 + 🔴 #FF3E3E + 🔵 #00E5FF |
| **Tipografía** | Orbitron (títulos) + Inter (cuerpo) + JetBrains Mono (código) |
| **Mascota** | Rotom — Forma Pokédex (Eléctrico/Fantasma) |
| **Tono** | Técnico profesional en español, inglés para código |
| **Logo** | "PokeData OS" con Pokéball digital en la "O" |
| **Fondo** | Siempre oscuro (#121214) |
| **Glow** | Azul Eléctrico (#00E5FF) en hover, selección, acentos |
| **Archivos** | `assets/logo/`, `assets/mascot/` |

---

> *"Los datos encuentran su forma."*  
> Manual de Identidad Visual — PokeData OS © 2026 DOSW
