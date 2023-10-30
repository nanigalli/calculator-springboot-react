import { render, screen } from '@testing-library/react';
import Screen from './Screen';

test('render a screen', () => {
    render(<Screen value="3.5" />);
    const linkElement = screen.getByText('3.5');
    expect(linkElement).toBeVisible;
});

test('render a screen with a big number', () => {
    render(<Screen value="111 111 111 111 111.5" />);
    const linkElement = screen.getByText('111 111 111 111 111.5');
    expect(linkElement).toBeVisible;
});