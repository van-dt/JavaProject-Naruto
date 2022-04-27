package com.game.ui;

import javax.swing.JPanel;

import com.game.effect.Animation;
import com.game.effect.CacheDataLoader;
import com.game.effect.FrameImage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable, KeyListener {

  private Thread thread;
  private boolean isRunning;

  private InputManager inputManager;

  private BufferedImage bufImage;
  private Graphics2D buf2D;

  // FrameImage frame1, frame2, frame3, frame4, frame5, frame6, frame7;

  BufferedImage image;
  BufferedImage subImage;

  public GamePanel() {
    inputManager = new InputManager();
    this.bufImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);

    // test cache dataloader animation
    // frame1 = CacheDataLoader.getInstance().getFrameImage("run1");
    // anm1 = CacheDataLoader.getInstance().getAnimation("robotRbullet");
    // anm1.flipAllImage();

    // test animation
    // try {
    // BufferedImage image = ImageIO.read(new File("data/megasprite.png"));
    // BufferedImage image1 = image.getSubimage(529, 30, 80, 100);
    // frame1 = new FrameImage("frame1", image1);
    // BufferedImage image2 = image.getSubimage(614, 30, 80, 100);
    // frame2 = new FrameImage("frame2", image2);
    // BufferedImage image3 = image.getSubimage(704, 30, 80, 100);
    // frame3 = new FrameImage("frame3", image3);
    // BufferedImage image4 = image.getSubimage(792, 30, 80, 100);

    // anm = new Animation();
    // anm.addFrame(frame1, 500 * 1000000);
    // anm.addFrame(frame2, 500 * 1000000);
    // anm.addFrame(frame3, 500 * 1000000);

    // } catch (IOException e) {
    // e.printStackTrace();
    // }

  }

  @Override
  public void paint(Graphics g) {

    g.drawImage(bufImage, 0, 0, this);
    // Graphics2D g2 = (Graphics2D) g;
    // frame1.draw(g2, 30, 30);
    // anm1.draw(300, 300, g2);

    // try {
    // image = ImageIO.read(new File("data/megasprite.png"));
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // BufferedImage image2 = image.getSubimage(614, 30, 80, 100);
    // g.drawImage(image2, 100, 130, null);

    // Graphics2D g2 = (Graphics2D) g;
    // anm.update(System.nanoTime());
    // anm.draw(100, 130, g2);
  }

  public void renderGame() {
    if (this.bufImage == null) {
      this.bufImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }
    if (this.bufImage != null) {
      this.buf2D = (Graphics2D) this.bufImage.getGraphics();
    }
    if (this.buf2D != null) {
      this.buf2D.setColor(Color.WHITE);
      this.buf2D.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
      this.buf2D.setColor(Color.red);
      this.buf2D.fillRect(40, 50, 100, 100);
    }
  }

  public void startGame() {
    if (thread == null) {
      isRunning = true;
      thread = new Thread(this);
      thread.start();
    }
  }

  @Override
  public void run() {

    long FPS = 80;
    long period = (1000 * 1000000) / FPS; // chu ki
    long beginTime;
    long sleepTime;

    System.out.println("run thread");

    beginTime = System.nanoTime();
    while (isRunning) {
      // System.out.println("a = " + a++);
      // update game
      // render game
      this.renderGame();
      this.repaint();
      // anm1.update(System.nanoTime());
      // this.repaint();
      // System.out.println("run");
      long deltaTime = System.nanoTime() - beginTime;
      sleepTime = period - deltaTime;
      // System.out.println(sleepTime);
      try {
        if (sleepTime > 0) {
          Thread.sleep(sleepTime / 1000000);
        } else {
          Thread.sleep(period / 2000000);
        }
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      beginTime = System.nanoTime();
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
    // System.out.println("keyPressed");
    // if (e.getKeyCode() == KeyEvent.VK_A) {
    // System.out.println("press A");
    // }
    inputManager.processKeyPressed(e.getKeyCode());
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // System.out.println("keyReleased");
    inputManager.processKeyReleased(e.getKeyCode());
  }
}